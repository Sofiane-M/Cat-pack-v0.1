package Client;

import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class client {
    
	public static void main(String[] args) {
  	  
		try {
		 Socket soc = new Socket("localhost", 5555);
  	     OutputStream os = soc.getOutputStream();
  	     PrintWriter pw = new PrintWriter(os,true);//printWriter is a new methode biha n9ado naba3to des charcter kol khatra charcter wahad 
  	     Scanner s =new Scanner(System.in);
  	     System.out.print("enter le mot  :");
  	     String mot =s.nextLine();
  	     pw.println(mot);;
  	    
  	     soc.close();
		
		}catch (IOException e) {
            System.out.println("Error connecting to the server or communicating with it.");
            e.printStackTrace();
        }
		
	
	}
}
