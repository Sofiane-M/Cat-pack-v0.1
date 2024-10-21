
package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class server {
    
	public static void main(String[] args) {
  	  
		try {
		 ServerSocket ss = new ServerSocket(5555);
		 Socket s = ss.accept();
  	     OutputStream os = s.getOutputStream();
  	     InputStream is = s.getInputStream();
  	     InputStreamReader isr = new InputStreamReader(is); //for claim the characters(7orof) one by one
  	     BufferedReader br = new BufferedReader(isr); //yjama3 ga3 l7orof fi mot wahad
  	     String mot = br.readLine();
  	     System.out.println("your string :"+mot);
  	   
  	    
  	     s.close();
		
		}catch (IOException e) {
            System.out.println("Error connecting to the server or communicating with it.");
            e.printStackTrace();
        }
		
	
	}
}
