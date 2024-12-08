/*
(project)RMI_Server/
    Calculator_implémente.java
    Calculator_server.java
(project)RMI_Client/
    Calculator_client.java
(project)RMI_shared/
    Calculator_interface.java
*/


//step 1

// new project (name = RMI_Shared) -> Create package (name =rmi_shared) 

//new interface (name = Calculator_interface)  Add java.rmi before create the interface
//code:
package rmi_shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator_interface extends Remote {
    double add(double a, double b) throws RemoteException;
    double sub(double a, double b) throws RemoteException;
    double mul(double a, double b) throws RemoteException;
    double div(double a, double b) throws RemoteException;
}


//step 2
// new project (name = RMI_Server) -> Create package (name =rmi_server)                      Right-click fal RMI_Server(project) Properties > Java Build Path > Projects mamb3d Add and select the RMI_Shared project click OK
//new class (name = Calculator_implémente ) Add Calculator_interface and browse the superclass = UnicastRemoteObject
//code:
package rmi_server;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import rmi_shared.Calculator_interface;

public class Calculator_implémente extends UnicastRemoteObject implements Calculator_interface {

	public Calculator_implémente() throws RemoteException {
		super();
	}

	
	public double add(double a, double b) throws RemoteException {
		return a+b;
	}
	 
	public double sub(double a, double b) throws RemoteException {
		return a-b;
	}
	 
	public double mul(double a, double b) throws RemoteException {
		return a*b;
	}
	 
	public double div(double a, double b) throws RemoteException {
		if (b == 0) {
            throw new RemoteException("Division par zéro non autorisée.");
        }
        return a / b;
	}	
}


//another class (name= Calculator_server) Add Registry.rmi
//code:
package rmi_server;


import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;


public class Calculator_server {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(5099); 
            Calculator_implémente calculateur = new Calculator_implémente();
            Naming.rebind("rmi://localhost:5099/calculateur", calculateur);
            System.out.println("Serveur démarré et Calculateur enregistré !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//step 3 
// new project (name = RMI_client) -> Create package (name =rmi_client)                      Right-click fal RMI_client(project) Properties > Java Build Path > Projects mamb3d Add and select the RMI_Shared project click OK
//new class (name = Calculator_client ) Add Calculator_interface

//code 

package rmi_client;


import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;



import javax.swing.*;

import rmi_shared.Calculator_interface;



public class Calculator_client extends JFrame {
	
	  private String operator = "";
      private double firstOperand = 0;
    private JTextField textField;
    private Calculator_interface calculateur;

    public  Calculator_client() {
        try {
            calculateur = (Calculator_interface) Naming.lookup("rmi://localhost:5099/calculateur");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Unable to connect to RMI server.");
            System.exit(1);
        }

        setTitle("RMI Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.BOLD, 24));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(false);
        textField.setBackground(new Color(220, 220, 220));
        textField.setForeground(Color.BLACK); 
        textField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 3)); 
        textField.setFocusable(false);
        add(textField, BorderLayout.NORTH);

       
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));

        
        String[] buttons = {"7", "8", "9", "/",
                            "4", "5", "6", "*",
                            "1", "2", "3", "-",
                            "C", "0", "=", "+"};

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            if ("/*-+".contains(text)) {
                button.setBackground(Color.ORANGE); 
                button.setForeground(Color.WHITE); 
            } else if ("C".equals(text)) {
                button.setBackground(Color.RED); 
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(Color.LIGHT_GRAY); 
                button.setForeground(Color.BLACK);
            }
            buttonPanel.add(button);

            button.addActionListener(new ButtonClickListener());
        }
  
        add(buttonPanel, BorderLayout.CENTER);
    }

    
    private class ButtonClickListener implements ActionListener {
      

 
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            try {
                System.out.println("Command: " + command);
                if ("0123456789".contains(command)) {
                    textField.setText(textField.getText() + command);
                } else if ("/*-+".contains(command)) {
                	
                	operator = command; // Set the operator regardless of textField content
                    if (!textField.getText().isEmpty()) {
                        firstOperand = Double.parseDouble(textField.getText());
                        textField.setText("");
                    } else {
                        textField.setText(""); // Optionally clear the field if empty
                    }
                }else if ("=".equals(command)) {
                    if (!textField.getText().isEmpty()) {
                        double secondOperand = Double.parseDouble(textField.getText());
                        double result = 0;

                        switch (operator) {
                            case "+":
                                result = calculateur.add(firstOperand, secondOperand);
                                break;
                            case "-":
                                result = calculateur.sub(firstOperand, secondOperand);
                                break;
                            case "*":
                                result = calculateur.mul(firstOperand, secondOperand);
                                break;
                            case "/":
                                if (secondOperand != 0) {
                                    result = calculateur.div(firstOperand, secondOperand);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Cannot divide by zero!");
                                    textField.setText("");
                                    return;
                                }
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Operator not recognized!");
                                return;
                        }
                        textField.setText(String.valueOf(result));
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter a number to calculate!");
                    }
                } else if ("C".equals(command)) {
                    textField.setText("");
                    firstOperand = 0;
                    operator = "";
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	 Calculator_client calculatorGUI = new  Calculator_client();
            calculatorGUI.setVisible(true);
        });
    }
}

