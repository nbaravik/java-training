import org.apache.log4j.Logger;

import java.io.*;
import java.lang.instrument.IllegalClassFormatException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class);

    private static final int DEFAULT_PORT = 8000;

    private static MessageStrategy messages = new SerializableStrategy();
    //private static MessageStrategy messages = new JsonStrategy();

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
                LOGGER.debug("Message \"" + receivedMessage.getMessage() + "\" from client#" + receivedMessage.getUserId() +
                        " was received at " + receivedMessage.getDateTime().format(DateTimeFormatter.ISO_TIME));

                for (Map.Entry<Integer, ObjectOutputStream> entry : objectOutputStreamsMap.entrySet()) {
                    if (entry.getKey() != client) {
                        try {
                            messages.send(entry.getValue(), receivedMessage);
                            LOGGER.debug("Message from client#" + client + " was sent to client#" + entry.getKey());
                        } catch (SocketException e) {
                            LOGGER.warn("Client#" + entry.getValue() + " was disconnected from server. " +
                                    "Message from client#" + client + " wasn't sent");
                        }
                    }
                }
            }
        } catch (SocketException | EOFException e) {
            LOGGER.info("Client #" + client + " was disconnected from server");
        } catch (IllegalStateException e) {
            LOGGER.error("Client #" + client + " was disconnected from server. The cause: IllegalStateException.");
        } catch (IOException e) {
            LOGGER.error("Unexpected exception: " + e);
        } finally {
            objectOutputStreamsMap.remove(client);
        }
    }

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> LOGGER.info("Server stopped by user")));

        int portNumber = DEFAULT_PORT;
        if (args.length > 0) {
            portNumber = Integer.parseInt(args[0]);
        }

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            if (portNumber == DEFAULT_PORT) {
                LOGGER.warn("Server started on default port " + DEFAULT_PORT);
            } else {
                LOGGER.info("Server started on port " + portNumber);
            }

            int countClients = 0;
            while (true) {
                Socket clientSocket = serverSocket.accept();
                countClients++;
                int clientId = countClients;
                LOGGER.info("Client #" + clientId + " is accepted");

                // create a new Thread for each new Client
                new Thread(() -> startCommunication(clientSocket, clientId)).start();
            }
        } catch (Exception e) {
            LOGGER.error("Unexpected exception: " + e);
        } finally {
            LOGGER.info("Server stopped");
        }
    }
}