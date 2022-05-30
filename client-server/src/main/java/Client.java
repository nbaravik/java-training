import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Client {

    private static int client_id;

    private static void readFromServer(Socket clientSocket) {

        try (ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())) {
            client_id = ois.readInt();
            System.out.println("Hello! You are user #" + client_id);
            while (true) {

                Message responseMessage = (Message) ois.readObject();
                System.out.println(responseMessage.toString());
            }
        } catch (SocketException se) {
            System.out.println("You were disconnected from server.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void writeToServer(Socket clientSocket) {

        try (ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String request = scanner.nextLine();
                Message requestMessage = new Message(client_id, LocalDateTime.now(), request);
                oos.writeObject(requestMessage);
                oos.flush();
                if (request.equalsIgnoreCase(Message.EXIT)) {
                    return;
                }
            }
        } catch (SocketException se) {
            System.out.println("You were disconnected from server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        try (
                Socket clientSocket = new Socket("127.0.0.1", 8000);
        ) {
            // read from server in a new Thread
            new Thread(() -> readFromServer(clientSocket)).start();
            // write to server in main
            writeToServer(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}