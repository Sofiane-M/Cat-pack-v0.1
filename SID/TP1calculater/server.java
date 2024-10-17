
package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class server {

	 
	public static void main(String[] args) {
	  	  
		try {
		 ServerSocket ss = new ServerSocket(5555);
		 Socket s = ss.accept();
		 InputStream is = s.getInputStream();
		 OutputStream os = s.getOutputStream();
		 int op1 = is.read();
		 int op2 = is.read();
		 int op = is.read();
		 int res = 0;
		 switch (op) {
		   
		 case 1 : 
			res = op1 + op2	;
			os.write(res);
			break;
		 
		 case 2 :
			 res = (op1 - op2) ;
			 os.write(res);
			 break;
			 
		 case 3 :
			 res = op1 * op2 ;
			 os.write(res);
			 break;
			 
		 case 4 :
			 if(op2 == 0 ) { 
			 System.out.println("Division by 0 is not allowed."); //Division by 0 case
             res = 0;
             os.write(res);
			 }
			 else {
			 res = (op1 / op2) ;
			 os.write(res);
			 }
			 break;
		 
		 }

		 s.close();
		} catch (IOException e) {
            // Handle the exception
            System.out.println("Error starting the server or accepting client connection.");
            e.printStackTrace();
        } 
 	 
		
		}
}
