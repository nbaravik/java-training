import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Client {

    private static int clientId;

    private static MessageStrategy messages = new SerializableStrategy();
    //private static MessageStrategy messages = new JsonStrategy();

    private static String formatMessage(Message msg) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Message.TIME_FORMAT_PATTERN);
        return String.format("user#%d %s: %s", msg.getUserId(), msg.getDateTime().format(formatter), msg.getMessage());
    }

    private static void readFromServer(Socket clientSocket) {
        try (
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            clientId = ois.readInt();
            System.out.println("Hello! You are user #" + clientId);
            while (true) {
                Message responseMessage = messages.receive(ois);
                System.out.println(formatMessage(responseMessage));
            }
        } catch (SocketException se) {
            System.out.println("Connection to server is lost.");
        } catch (IOException e) {
            System.out.println("Unexpected problem: " + e);
        }
    }

    private static void writeToServer(Socket clientSocket) {
        try (
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())
        ) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String request = scanner.nextLine();
                Message requestMessage = new Message(clientId, LocalDateTime.now(), request);
                messages.send(oos, requestMessage);
            }
        } catch (SocketException se) {
            System.out.println("Connection to server is lost.");
        } catch (IOException e) {
            System.out.println("Unexpected problem: " + e);
        }
    }

    public static void main(String[] args) {
        int portNumber = 8000;
        if (args.length > 0) {
            portNumber = Integer.parseInt(args[0]);
        }
        try (
                Socket clientSocket = new Socket("127.0.0.1", portNumber);
        ) {
            // read from server in a new Thread
            new Thread(() -> readFromServer(clientSocket)).start();
            // write to server in main
            writeToServer(clientSocket);
        } catch (IOException e) {
            System.out.println("An attempt to connect to host 127.0.0.1 on port " + portNumber + " failed.");
        }
    }
}