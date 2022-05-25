import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.Scanner;

public class Client extends JFrame {

    private static BufferedWriter writer;
    private static BufferedReader reader;

    private static void readFromServer(Socket clientSocket) {


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            while( true) {
                String response = reader.readLine();
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        try (Socket clientSocket = new Socket("127.0.0.1", 8000)) {

            Scanner scanner = new Scanner(System.in);


            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            new Thread(() -> {
                readFromServer(clientSocket);
            }).start();

            while (true) {

                String request = scanner.nextLine();
                writer.write(request);
                writer.newLine();
                writer.flush();

//            String response = objectMapper.readValue(jsonFile, String.class);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
            reader.close();
        }
    }
}


//              ObjectMapper objectMapper = new ObjectMapper();
//                       objectMapper.writeValueAsString("Server, I send you a test-message.");

//            File jsonFile = new File("test.json");
//            objectMapper.writeValue(jsonFile, request);
//            String jsonString = objectMapper.writeValueAsString(request);
//            writer.write(jsonString);