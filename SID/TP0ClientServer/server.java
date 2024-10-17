package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class server {

	 
	public static void main(String[] args) {
	  	  
		try {
		 ServerSocket ss = new ServerSocket(5555);//server socket . that listens for incoming connections on port 5555 for example.
		 Socket s = ss.accept();//The accept() method blocks (waits) until a client connects to the server. When a client connects, it creates a new Socket object (s) that represents the connection to the client.
		 InputStream is = s.getInputStream();
		 OutputStream os = s.getOutputStream();
		 int nbr = is.read();//This line reads a single byte of data sent by the client and stores it in the variable "nbr" .
		 nbr++;
		 os.write(nbr);//This line sends the incremented value back to the client using the output stream.(yraja3 la valeur nbr lal client using write mamb3d client ya9raha bal read)
		 s.close();//socket close
		 
		 
		
		
		} catch (IOException e) {
            // Handle the exception
            System.out.println("Error starting the server or accepting client connection.");
            e.printStackTrace();
        } 
 	 
		
		}
}
