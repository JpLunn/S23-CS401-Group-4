import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Client {
    public static void main(String[] args) {
        try {
        	GUI clientGUI;
            // Create a socket to connect to the server on port 8000
            Socket socket = new Socket("localhost", 8000);
            
            // Get the input and output streams for the socket
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            // Send a message to the server
            out.println("Hello from the client");
            
            // Read the server's response
            String response = in.readLine();
            System.out.println("Received from server: " + response);
            
            clientGUI = new GUI();
            // Close the input, output, and socket
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
