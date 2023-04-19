import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Server {
    public static void main(String[] args) {
        try {
            // Create a ServerSocket to listen for incoming connections on port 8000
            ServerSocket serverSocket = new ServerSocket(8000);
            
            while (true) {
                // Wait for a client to connect
                Socket clientSocket = serverSocket.accept();
                
                // Create a new thread to handle the client's connection
                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    
    public void run() {
        try {
            // Get the input and output streams for the client socket
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            
            // Read input from the client and send a response
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from client: " + inputLine);
                out.println("Server response: " + inputLine);
            }
            
            // Close the input, output, and client socket
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
