package Tp1;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
  //hna ma dartsh bal constructure psk shoft bali had tari9a sahla ktar + khdamt direct bal frame mshi bal panel psk frame 7asitha more professionnelle we tanik capable fiha swala7 ta3 zwa9 ktar n9ado ndirohom
 //NOTE: panel is just a container yji fal frame mais frame hiya la fenetre compler tsema frame dayman tkon kayna mais hna khdamt biha direct


public class appB extends JFrame {

	public static int Nbr;
  
	public static void main(String[] args){
		
		JFrame f = new JFrame("Exo 03");
		f.setSize(600, 600);
        
        // Set the layout
        f.setLayout(new FlowLayout());
      
        // Make the frame visible
        f.setVisible(true);
       
        Nbr = (int) (Math.random() * 100) + 1;
        JLabel label = new JLabel("Entrez un nombre entre 1 et 100:");
        JTextField tf = new JTextField(5);
        JButton submite = new JButton("Go!");
        JLabel labelResult = new JLabel("");
        
        f.add(label);
        f.add(tf);
        f.add(submite);
	    f.add(labelResult);
	
          submite.addActionListener(new ActionListener() {
        	
        	public void actionPerformed(ActionEvent e){
               int userguesse = Integer.parseInt(tf.getText());
        		if(userguesse > Nbr){labelResult.setText("try something less bigger!!");} 
        		if(userguesse < Nbr){labelResult.setText("try something More bigger!!");} 
        		if(userguesse == Nbr){
        	   labelResult.setText("YOU won");
        	   submite.setEnabled(false);} 
               }
        	
        });
		 
	}






















}
