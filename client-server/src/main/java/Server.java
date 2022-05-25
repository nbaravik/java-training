import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Server {

    public static int countClients = 0;

    private static Map<Integer, Socket> socketMap = new ConcurrentHashMap<>();


    private static void startCommunication(Socket socket, int client) {

        try (
                Socket clientSocket = socket;
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {

            while (true) {
                String request = reader.readLine();
                boolean endOfStream = ( request == null );
                if (endOfStream) {
                    return;
                }
                System.out.println(request + "   client = " + client);
                System.out.println(socketMap);
                for (Map.Entry<Integer, Socket> entry : socketMap.entrySet()) {
                    if ( entry.getKey() != client ) {
                        entry.getValue().getOutputStream().write(request.getBytes());
                        entry.getValue().getOutputStream().write('\n');
                        entry.getValue().getOutputStream().flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socketMap.remove(client);
        }

    }

    public static void main(String[] arg) {

        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Server started");

            while (true) {

                Socket clientSocket = serverSocket.accept();
                countClients++;
                int client = countClients;

                socketMap.put(client, clientSocket);
                System.out.println("client accepted " + client);

                new Thread(() -> {
                    startCommunication(clientSocket, client);
                }).start();

//                ObjectMapper objectMapper = new ObjectMapper();
//                File jsonFile = new File("test.json");
//                String request = objectMapper.readValue(jsonFile, String.class);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}