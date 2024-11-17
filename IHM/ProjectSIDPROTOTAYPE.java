package client;
import java.net.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.*;
import java.util.*;
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
            Socket socket = new Socket("localhost", 12346);
            OutputStream os = socket.getOutputStream();           
            pw = new PrintWriter(os, true);
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
            // Build JFrame to send message
            JFrame frame = new JFrame("Client Chat");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);

            chatArea = new JTextArea();
            chatArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(chatArea);

            inputField  = new JTextField();
            targetField = new JTextField();
            messageType = new JComboBox<>(new String[]{"Unicast", "Broadcast"});
            JLabel label = new JLabel("Enter the number of the client :");
            targetField.setBorder(BorderFactory.createCompoundBorder(
            	    targetField.getBorder(),
            	    BorderFactory.createEmptyBorder(5, 5, 5, 50)
            	));
            
            messageType.addActionListener(e -> {
                if (messageType.getSelectedItem().equals("Unicast")) {
                	targetField.setEnabled(true);
                	targetField.repaint();   // Refresh the UI from documentation
                    targetField.revalidate();
                } else {
                	targetField.setEnabled(false);
                }
            });
             
            
            JButton sendButton = new JButton("Send");
            sendButton.addActionListener(e -> sendMessage());

            JPanel inputPanel = new JPanel(new BorderLayout());
            JPanel panel2 = new JPanel();
            
            inputPanel.add(messageType, BorderLayout.WEST);
            inputPanel.add(inputField, BorderLayout.CENTER);
            inputPanel.add(sendButton, BorderLayout.EAST);

            panel2.add(label);
            
            
            JPanel southPanel = new JPanel(new BorderLayout());
            southPanel.add(panel2, BorderLayout.NORTH); // Add label panel at the top
            southPanel.add(targetField, BorderLayout.SOUTH); 
            
            
            frame.add(southPanel , BorderLayout.SOUTH);
            frame.add(scrollPane, BorderLayout.CENTER);
            frame.add(inputPanel, BorderLayout.NORTH);

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

        if (messageType.getSelectedItem().equals("Broadcast")) {
            pw.println("broadcast:" + message);
            chatArea.append("You: " + message + "\n");
        } else {
            String targetId = targetField.getText().trim();
            if (!targetId.isEmpty()) {
                pw.println("unicast " + targetId + ":" + message);
                chatArea.append("Sent to "+targetId+ " :" + message + "\n");
            } else {
                chatArea.append("Error: Please enter a target client ID for unicast.\n");
            }
        }
       
        
        
        inputField.setText("");
    }
}




























