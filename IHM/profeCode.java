package tp1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Profe extends JFrame {
	private JTextField usernameField;
	private JPasswordField passwordField;
	public Profe() {
        setTitle("Login");
        setSize(400,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(3,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
        panel.add(new JLabel("Nom d'utilisateur"));
        usernameField = new JTextField();
        panel.add(usernameField);
        
        panel.add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField();
        panel.add(passwordField);
        
        JButton loginButton = new JButton("Connexion");
        loginButton.addActionListener(new LogicButtonListener());
        panel.add(loginButton);
        add(panel);
        
        JButton inscriButton = new JButton("inscrit");
    
	}
	 private class LogicButtonListener implements ActionListener {
		 @Override
		 public void actionPerformed(ActionEvent e) {
			 String username = usernameField.getText();
			 String password = new String(passwordField.getPassword());
			 
			 if(username.equals("admin") && password.endsWith("password")) {
				 JOptionPane.showMessageDialog(Profe.this,"connexion reussie !");
			 }else {
				 JOptionPane.showMessageDialog(Profe.this,"failed");
			 } 
		 } 
	 }
	 public static void main(String[] args) {
		 SwingUtilities.invokeLater(() -> {
			 Profe profe = new Profe();
			 profe.setVisible(true);
			 
		 });
		  
	 }
