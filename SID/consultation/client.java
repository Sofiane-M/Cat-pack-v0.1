package client;

import java.net.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;

public class Client {
    private static PrintWriter pw;
    private static JTextArea chatArea;
    private static JTextField inputField;
    private static JComboBox<String> messageType;
    private static JTextField targetField;
    private static String username;

    public static void main(String[] args) {
        try {
            // Build username frame
            JFrame usernameFrame = new JFrame("Enter Username");
            usernameFrame.setSize(400, 200);
            usernameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            usernameFrame.setLocationRelativeTo(null);

            JPanel usernamePanel = new JPanel(new GridLayout(3,2,10,10));
            usernamePanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
            JLabel usernameLabel = new JLabel("Enter your username:");
            JTextField usernameField = new JTextField();
            JButton connectButton = new JButton("Connect");

            usernamePanel.add(usernameLabel);
            usernamePanel.add(usernameField);
            usernamePanel.add(connectButton);

            usernameFrame.add(usernamePanel);
            usernameFrame.setVisible(true);

            connectButton.addActionListener(e -> {
                username = usernameField.getText().trim();
                if (!username.isEmpty()) {
                    usernameFrame.dispose();
                    startChat();
                } else {
                    JOptionPane.showMessageDialog(usernameFrame, "Username cannot be empty.");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void startChat() {
        try {
        	
            Socket socket = new Socket("localhost", 1234);
            OutputStream os = socket.getOutputStream();
            pw = new PrintWriter(os, true);
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            // Send username to server
            pw.println("username:" + username);

            // Build chat frame
            JFrame frame = new JFrame("Chat Client - " + username);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 600);

            chatArea = new JTextArea();
            chatArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(chatArea);

            inputField = new JTextField();
            targetField = new JTextField();
            messageType = new JComboBox<>(new String[]{"Broadcast", "Unicast", "Multicast"});
            messageType.setPreferredSize(new Dimension(150, 25)); // Width: 150, Height: 25
            messageType.setMaximumSize(new Dimension(150, 25));
            
            
            JLabel label = new JLabel("Enter client u want to send to  (comma , separated for Multicast) and only name for unicast:");
            targetField.setEnabled(false);
 
          messageType.addActionListener(e -> {
              String selectedType = (String) messageType.getSelectedItem();
              if ("Broadcast".equals(selectedType)) {
                 targetField.setEnabled(false); 
                  targetField.setText("");      
               } else {
                   targetField.setEnabled(true); 
                }
            });

            JButton sendButton = new JButton("Send");
            sendButton.addActionListener(e -> sendMessage());

            JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
            JPanel unicastPanel = new JPanel(new BorderLayout());
            JPanel panel9 = new JPanel();
            panel9.add(messageType);
            
            
            //inputPanel.add(panel9, BorderLayout.WEST);
            inputPanel.add(inputField, BorderLayout.CENTER);
            inputPanel.add(sendButton, BorderLayout.EAST);

            unicastPanel.add(label, BorderLayout.NORTH);
            unicastPanel.add(targetField, BorderLayout.CENTER);

            JPanel southPanel = new JPanel(new BorderLayout());
            southPanel.add(unicastPanel, BorderLayout.NORTH);
            southPanel.add(inputPanel, BorderLayout.SOUTH);

            frame.add(scrollPane, BorderLayout.CENTER);
            frame.add(southPanel, BorderLayout.SOUTH);
            frame.add(panel9, BorderLayout.WEST);
            
            frame.setVisible(true);

            // Thread to handle incoming messages
            new Thread(() -> {
                try {
                    String message;
                    while ((message = br.readLine()) != null) {
                        chatArea.append(message + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
    private static void sendMessage() {
        String message = inputField.getText().trim();
        if (message.isEmpty()) return;

        String target = targetField.getText().trim();
        String command;

     // Broadcast       
        if (messageType.getSelectedItem().equals("Broadcast")) {     	
        	command = "broadcast:" + message;
            chatArea.append("You (Broadcast): " + message + "\n");
   
         // unicast
        } else if (messageType.getSelectedItem().equals("Unicast")) {
            if (!target.isEmpty()) {
        	
                command = "unicast " + target + ":" + message;
                chatArea.append("You (To " + target + "): " + message + "\n");
            } else {
                chatArea.append("Error: Please enter a recipient's username for Unicast.\n");
                return;
                
            }
        } else { // Multicast
            if (!target.isEmpty()) {
                command = "multicast " + target + ":" + message;
                chatArea.append("You (To group " + target + "): " + message + "\n");
            } else {
                chatArea.append("Error: Please enter recipient usernames for Multicast.\n");
                return;
            }
        }

        pw.println(command);
        inputField.setText("");
    }
}
