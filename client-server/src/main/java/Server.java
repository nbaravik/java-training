import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

//  private static MessageStrategy messages = new SerializableStrategy();
    private static MessageStrategy messages = new JsonStrategy();

    private static Map<Integer, ObjectOutputStream> objectOutputStreamsMap = new ConcurrentHashMap<>();

    private static void startCommunication(Socket socket, int client) {
        try (
                Socket clientSocket = socket;
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
        ) {

            objectOutputStreamsMap.put(client, oos);
            oos.writeInt(client);
            oos.flush();

            while (true) {
                Message receivedMessage = messages.receive(ois);

                for (Map.Entry<Integer, ObjectOutputStream> entry : objectOutputStreamsMap.entrySet()) {
                    if (entry.getKey() != client) {
                        try {
                            messages.send(entry.getValue(), receivedMessage);
                        } catch (SocketException e) {
                            System.out.println("Client #" + client + " was disconnected from server");
                        }
                    }
                }
            }
        } catch (SocketException e) {
            System.out.println("client #" + client + " was disconnected from server");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            objectOutputStreamsMap.remove(client);
        }
    }

    public static void main(String[] args) {

        int portNumber = 8000;
        if (args.length > 0) {
            portNumber = Integer.parseInt(args[0]);
        }

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server started on port " + portNumber);
            int countClients = 0;

            while (true) {
                Socket clientSocket = serverSocket.accept();
                countClients++;
                int clientId = countClients;
                System.out.println("client #" + clientId + " is accepted");

                // create a new Thread for each new Client
                new Thread(() -> startCommunication(clientSocket, clientId)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}