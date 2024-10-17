package client;

import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class client {
    
	public static void main(String[] args) {
  	  
		try {
		 Socket soc = new Socket("localhost", 5555);
  	     InputStream is = soc.getInputStream();
  	     OutputStream os = soc.getOutputStream();
  	     
  	     System.out.print("enter OP1  :");
  	     Scanner s =new Scanner(System.in);
  	     int op1 = s.nextInt() ;
  	     os.write(op1);
  	     
  	     System.out.print("enter OP2  :");
	     Scanner ss =new Scanner(System.in);    // the bug was in scan we should put ss not s cause that's another variable :)
	     int op2 = ss.nextInt() ;
	     os.write(op2);
  	     
	     System.out.println("number 1 pour add ||number 2 pour sus ||number 3 pour mut ||number 4 pour sub");
	     System.out.print("votre choix: ");
	     Scanner sss =new Scanner(System.in);  // here too we should put sss not s
	     int op = sss.nextInt() ;
	     os.write(op);
	     
	     
  	     int res = is.read();
  	     System.out.println("the resulte : "+ res);
  	     soc.close();
		}catch (IOException e) {
            System.out.println("Error connecting to the server or communicating with it.");
            e.printStackTrace();
        }
		
	
	}
}
