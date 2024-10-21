package TP1;
import javax.swing.*;
import java.awt.*;
              //this class is extends from the main one JFrame just creat another file from the packige and use the code be carfule to name it according to your file :)
public class ButtonChoice extends JFrame {
    
	
	protected ButtonChoice() {  //constructure of the ButtonChoice class
        // Set layout for the frame
    	JPanel panel = new JPanel(); //create container
        //panel.setLayout(new FlowLayout()); set the flow layout to the panel to display elements from right to left
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //set BoxLayout to display elemnts from top to buttom (bah yjo radio bottons msatfin mal fog lal ta7t bal BoxLayout methode)
        // Creating Radio Buttons
        JRadioButton radio1 = new JRadioButton("Radio button"); //create constructor
        JRadioButton radio2 = new JRadioButton("Radio button");
        JRadioButton radio3 = new JRadioButton("Radio button");
        
        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        group.add(radio2); //add all radio buttons to a group
        group.add(radio3);
        
        panel.add(radio1);  //add all radio buttons to the panel 
        panel.add(radio2); 
        panel.add(radio3);

        
        
        //Creating Menu
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("Fichier");
        JMenu m2 = new JMenu("Aide");
        mb.add(m1);
        mb.add(m2);

        JMenuItem openItem = new JMenuItem("Ouvrir");
        JMenuItem saveItem = new JMenuItem("Sauvegarder");
        m1.add(openItem);
        m1.add(saveItem);
        setJMenuBar(mb); //add the menu

        
        JPanel inputPanel = new JPanel();
        
        // Creating Text Field and Button
        JLabel label = new JLabel("Enter your Text:");
        JTextField textField = new JTextField(10);  // Max 10 characters
        JButton sendButton = new JButton("Envoyer");
        inputPanel.add(label);
        inputPanel.add(textField);
        inputPanel.add(sendButton);

        add(panel); //add the panel to the page
        add(inputPanel, BorderLayout.SOUTH); //bah yji container ta3 inputpanel mal ta7t + add the inputpanel bal add lal page
        // Frame settings
        setSize(500, 200); //frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exit and open permission 
        setVisible(true);//visible
    
	
        
        
        
	}

    public static void main(String[] args) {
        new ButtonChoice(); //exucute le code
    }
}
