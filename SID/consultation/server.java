package server;

import java.net.*;
import java.io.*;
import java.util.*;


public class Server {
    private static Map<Integer, ClientHandler> clientsById = Collections.synchronizedMap(new HashMap<>());
    private static Map<String, ClientHandler> clientsByUsername = Collections.synchronizedMap(new HashMap<>());
    private static int clientIdCounter = 1;

    public static void main(String[] args) {
        try {
            
        	ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                int clientId = clientIdCounter++;
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientId); //ClientHandler: A class responsible for handling communication with a single client.
                clientsById.put(clientId, clientHandler); // clientsById A Map that associates each client’s unique ID with their corresponding ClientHandler instance.
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        
    // synchronized (clientsByUsername) { ... } Ensures thread safety when accessing or iterating over the clientsByUsername map.

    public static void broadcast(String message, String senderUsername) {
        synchronized (clientsByUsername) {
            for (ClientHandler client : clientsByUsername.values()) {
                if (!client.getUsername().equals(senderUsername)) {
                    client.sendMessage(senderUsername + " (broadcast): " + message);
                }
            }
        }
    }

    public static void unicast(String message, String senderUsername, String receiverUsername) {
        ClientHandler receiver = clientsByUsername.get(receiverUsername);
        if (receiver != null) {
            receiver.sendMessage(senderUsername + " (private): " + message);
        } else {
            ClientHandler sender = clientsByUsername.get(senderUsername);
            if (sender != null) {
                sender.sendMessage("Error: User " + receiverUsername + " is not connected.");
            }
        }
    }

    public static void multicast(String message, String senderUsername, String[] receiverUsernames) {
        for (String receiverUsername : receiverUsernames) {
            ClientHandler receiver = clientsByUsername.get(receiverUsername.trim());
            if (receiver != null) {
                receiver.sendMessage(senderUsername + " (multicast): " + message);
            } else {
                ClientHandler sender = clientsByUsername.get(senderUsername);
                if (sender != null) {
                    sender.sendMessage("Error: User " + receiverUsername + " is not connected.");
                }
            }
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private int clientId;
        private String username;
        private PrintWriter out;

        public ClientHandler(Socket socket, int clientId) {
            this.socket = socket;
            this.clientId = clientId;
        }

        public String getUsername() {
            return username;
        }

        @Override
        public void run() {
            try {
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                OutputStream os = socket.getOutputStream();
                out = new PrintWriter(os, true);

                // Read the username
                String initialInput = br.readLine();
                if (initialInput.startsWith("username:")) {
                    username = initialInput.substring(9).trim();

                    // Ensure the username is unique
                    synchronized (clientsByUsername) {
                        if (clientsByUsername.containsKey(username)) {
                            out.println("Error: Username " + username + " is already in use. Disconnecting...");
                            socket.close();
                            return;
                        }
                        clientsByUsername.put(username, this);
                    }
                    
                    

                    System.out.println("Client " + clientId + " identified as " + username);
                    out.println("Welcome, " + username + "!");
                
                
                
                } else {
                    out.println("Error: Username not provided. Connection closing.");
                    socket.close();
                    return;
                }

                
                
                // Handle incoming messages
                String input;
                while ((input = br.readLine()) != null) {
                    System.out.println("Received from " + username + ": " + input);
                    String[] parts = input.split(":", 2);

                    if (parts[0].equalsIgnoreCase("broadcast")) {
                        broadcast(parts[1], username);
                    
                        
                        
                    } else if (parts[0].startsWith("unicast")) {
                        String[] unicastParts = parts[0].split(" ", 2);
                        
                        if (unicastParts.length == 2) {
                        
                        	
                        	String targetUsername = unicastParts[1];
                            unicast(parts[1], username, targetUsername);
                        
                        
                        } else {
                            out.println("Error: Invalid unicast command.");
                        }
                    } else if (parts[0].startsWith("multicast")) {
                        
                    	
                    	String[] multicastParts = parts[0].split(" ", 2);
                        
                    	
                    	if (multicastParts.length == 2) {
                    
                    		String[] targetUsernames = multicastParts[1].split(",");
                            multicast(parts[1], username, targetUsernames);
                        
                    	
                    	} else {
                            out.println("Error: Invalid multicast command.");
                        }
                    
                    
                    } else {
                        out.println("Error: Invalid command.");
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
                synchronized (clientsByUsername) {
                    clientsByUsername.remove(username);
                }
                synchronized (clientsById) {
                    clientsById.remove(clientId);
                }
                System.out.println("Client " + clientId + " (" + username + ") disconnected.");
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }
    }
}
