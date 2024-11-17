package server;
import java.net.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class Server {
    private static Map<Integer, ClientHandler> clients = Collections.synchronizedMap(new HashMap<>());
    private static int clientIdCounter = 1;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12346);
            System.out.println("Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                int clientId = clientIdCounter++;
                System.out.println("Client " + clientId + " connected!");
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientId);
                clients.put(clientId, clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message, int senderId) {
        synchronized (clients) {
            for (ClientHandler client : clients.values()) {
                if (client.getClientId() != senderId) {
                    client.sendMessage("Client " + senderId + ": " + message);
                }
            }
        }
    }

    public static void unicast(String message, int senderId, int receiverId) {
        ClientHandler receiver = clients.get(receiverId);
        if (receiver != null) {
            receiver.sendMessage("Client " + senderId + " (private): " + message);
        } else {
            clients.get(senderId).sendMessage("Client " + receiverId + " is not connected.");
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private int clientId;
        private PrintWriter out;

        public ClientHandler(Socket socket, int clientId) {
            this.socket = socket;
            this.clientId = clientId;
        }

        public int getClientId() {
            return clientId;
        }

        @Override
        public void run() {
            try {
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                OutputStream os = socket.getOutputStream();
                out = new PrintWriter(os, true);

                out.println("Welcome! Your client ID is " + clientId);

                String input;
                while ((input = br.readLine()) != null) {
                	System.out.println("Received: " + input);
                    String[] parts = input.split(":", 2);
                    if (parts[0].equalsIgnoreCase("broadcast")) {
                        broadcast(parts[1], clientId);
                    } else if (parts[0].startsWith("unicast")) {
                    	 String[] unicastParts = parts[0].split(" ");
                    	 int targetId = Integer.parseInt(unicastParts[1]);
                    	 unicast(parts[1], clientId, targetId);
                    } else {
                        out.println("Invalid command.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                synchronized (clients) {
                    clients.remove(clientId);
                }
                System.out.println("Client " + clientId + " disconnected.");
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }
    }
}
