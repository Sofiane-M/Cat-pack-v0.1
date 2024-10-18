package Tp1;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;


public class appA extends JFrame {

	private int clicknbr = 0; 
	 protected BappA(){
    	
		setTitle("Gestion d’un événement de click");
      
    	JPanel container = new JPanel();
    	container.setLayout(new FlowLayout());
        setContentPane(container); // This allows us to customize the layout and organization of components within your window (3la 7sab documentation)
      
        JLabel label = new JLabel("Bouton Cliqué 0 fois");
        JButton btn = new JButton("Click");
        
        container.add(label);
        container.add(btn);
       
        btn.addActionListener(new ActionListener() {  //hadi sarilha kima addeventListener li kayna fal javascript mshi 7aga
        	
        	public void actionPerformed(ActionEvent e){
                 // Update the clicknbr
               clicknbr++;
               label.setText("Bouton has been clicked " + clicknbr + " times");
          
        	}
        	
        });
    	
      
      //setLayout(new FlowLayout());
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(200, 200, 400, 300);
      setSize(600, 600);
      setVisible(true);
    }
	
	
	
    
    
    public static void main(String[] args) {
    	 new appA(); //create a constructure to excute the code
    }
}
