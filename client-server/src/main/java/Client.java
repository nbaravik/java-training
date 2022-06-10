import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Client {

    private static int clientId;

//  private static MessageStrategy messages = new SerializableStrategy();
    private static MessageStrategy messages = new JsonStrategy();

    private static String formatMessage(Message msg) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Message.TIME_FORMAT_PATTERN);
        StringBuilder sb = new StringBuilder();
        sb.append("user#");
        sb.append(msg.getUserId());
        sb.append(" ");
        sb.append(msg.getDateTime().format(formatter));
        sb.append(": ");
        sb.append(msg.getMessage());
        return sb.toString();
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
        } catch (SocketException | EOFException se) {
            System.out.println("Connection to server is lost.");
        } catch (IOException e) {
            e.printStackTrace();
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
        } catch (SocketException | EOFException se) {
            System.out.println("Connection to server is lost.");
        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}