
package tp1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Profe extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton inscritButton;
    private static ArrayList<User> userList = new ArrayList<>();

    public Profe() {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nom d'utilisateur"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Connexion");
        loginButton.addActionListener(new LogicButtonListener());
        panel.add(loginButton);

        inscritButton = new JButton("Inscrit");
        inscritButton.addActionListener(new InscritButtonListener());
        panel.add(inscritButton);

        add(panel);
    }

    private class LogicButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            
            for (User user : userList) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(Profe.this, "Connexion réussie!");
                 
                    showHelloFrame(user.getUsername());
                    return;
                }
            }

            JOptionPane.showMessageDialog(Profe.this, "Échec de la connexion.");
        }
    }

    private class InscritButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           
            new RegistrationFrame().setVisible(true);
        }
    }

    private void showHelloFrame(String username) {
        JFrame frame1 = new JFrame("Hello " + username);
        frame1.setSize(500, 500);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        JLabel use = new JLabel("Hello: " + username);
        panel1.add(use);

        frame1.add(panel1, BorderLayout.CENTER);
        frame1.setVisible(true);
    }

    
    public static class User {
        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Profe profe = new Profe();
            profe.setVisible(true);
        });
    }

    
    private class RegistrationFrame extends JFrame {
        public RegistrationFrame() {
            setTitle("Inscription");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(4, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel userLabel = new JLabel("Nom d'utilisateur");
            JTextField userText = new JTextField();
            JLabel passLabel = new JLabel("Mot de passe :");
            JPasswordField passText = new JPasswordField();
            JLabel passLabelConfirm = new JLabel("Confirmation Mot de passe :");
            JPasswordField passTextConfirm = new JPasswordField();

            panel.add(userLabel);
            panel.add(userText);
            panel.add(passLabel);
            panel.add(passText);
            panel.add(passLabelConfirm);
            panel.add(passTextConfirm);

            JButton registerButton = new JButton("S'inscrire");
            registerButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String username = userText.getText();
                    String password = new String(passText.getPassword());
                    String cpassword = new String(passTextConfirm.getPassword());

                    if (password.equals(cpassword)) {
                        userList.add(new User(username, password)); 
                        JOptionPane.showMessageDialog(RegistrationFrame.this, "Inscription réussie!");
                        dispose(); 
                    } else {
                        JOptionPane.showMessageDialog(RegistrationFrame.this, "Les mots de passe ne correspondent pas.");
                    }
                }
            });

            panel.add(registerButton);
            add(panel);
        }
    }
}
