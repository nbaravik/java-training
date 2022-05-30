import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private static Map<Integer, Socket> socketMap = new ConcurrentHashMap<>();

    private static void startCommunication(Socket socket, int client) {

        try (
                Socket clientSocket = socket;
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
        ) {
            oos.writeInt(client);
            oos.flush();

            while (true) {
                Message receivedMessage = (Message) ois.readObject();
                if (receivedMessage.getMessage().equalsIgnoreCase(Message.EXIT)) {
                    System.out.println("client #" + receivedMessage.detId() + " was disconnected.");
                    return;
                }
                for (Map.Entry<Integer, Socket> entry : socketMap.entrySet()) {
                    if (entry.getKey() != client) {
                        ObjectOutputStream entryOOS = new ObjectOutputStream(entry.getValue().getOutputStream()) {
                            @Override
                            public void writeStreamHeader() {
                                return;
                            }
                        };
                        entryOOS.writeObject(receivedMessage);
                        entryOOS.flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            socketMap.remove(client);
        }
    }

    public static void main(String[] arg) {

        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Server started");
            int countClients = 0;

            while (true) {

                Socket clientSocket = serverSocket.accept();
                countClients++;
                int clientId = countClients;

                socketMap.put(clientId, clientSocket);
                System.out.println("client #" + clientId + " is accepted");

                // create a new Thread for each new Client
                new Thread(() -> startCommunication(clientSocket, clientId)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}