package client;
//be sure to create package that named client and then a file that named client (client.java)
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class client {
    
	public static void main(String[] args) {
  	  
		try {
		 Socket soc = new Socket("localhost", 5555); //creates a new Socket object
  	     InputStream is = soc.getInputStream(); //obtain the input and output streams for the socket. of course to use them (bah n3amrohom)
  	     OutputStream os = soc.getOutputStream();
  	     System.out.print("Enter a number :");
  	     Scanner s = new Scanner(System.in); //classic print and scan in java
  	     int n = s.nextInt();
  	     os.write(n); //This line sends the integer value n to the server. This is done by writing to the output stream
  	     int rep = is.read(); //This line reads a single byte of data sent by the server and stores it in the variable res. The read() method returns an integer representing the byte value (0-255). to be specific (from the documentation)
  	     System.out.println("the result :" + rep);
  	     soc.close(); //socket close
		}catch (IOException e) {
            System.out.println("Error connecting to the server or communicating with it."); //sometimes the programme work  even without catsh
            e.printStackTrace();
        }
		
	
	}
}

//InputStream and OutputStream: For reading from and writing to the socket.
//Socket: Represents the connection to the server.
//The try block contains code that might throw an exception (in this case, when creating the socket or reading/writing data). If an exception occurs, control will be transferred to the catch block.
//"is" is used to read data received from the server, and "os" is used to send data to the server. "is" for read and "os" for send



//if u noticed a yellow bug or error it's because the server socket are not close (Not a big deal cause the programme should work anyway)
