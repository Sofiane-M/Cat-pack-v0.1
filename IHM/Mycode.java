package tp1;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

public class Loginapp  extends JFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel userLabel1 = new JLabel("Nom d'utilisateur");
        JTextField userText = new JTextField();
        JLabel passLabel = new JLabel("Mot de pass :");
        JPasswordField passText = new JPasswordField();
        JLabel passLabelc = new JLabel("confirmation Mot de passe :");
        JPasswordField passText1 = new JPasswordField();

        panel.add(userLabel1);
        panel.add(userText);
        panel.add(passLabel);
        panel.add(passText);
        panel.add(passLabelc);
        panel.add(passText1);

        JButton loginButton = new JButton("se connecter");
        panel.add(loginButton);  

        frame.add(panel, BorderLayout.CENTER);  
        frame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passText.getPassword());
                String cpassword = new String(passText1.getPassword());  
                
                if (username.equals("sofi") && password.equals("sofi123") && cpassword.equals("sofi123")) {
                    JFrame frame1 = new JFrame("Hello " + username);
                    frame1.setSize(500, 500);
                    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    JPanel panel1 = new JPanel();
                    panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
                    JLabel use = new JLabel("Hello: " + username);
                    panel1.add(use);
                    
                    frame1.add(panel1, BorderLayout.CENTER);
                    frame1.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(frame, "REFUSED!!!!");
                }
            }
        });
    }
}
