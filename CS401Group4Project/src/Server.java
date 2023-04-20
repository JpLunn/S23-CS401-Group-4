import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Server {
    public static void main(String[] args) {
        ArrayList<User> userList = new ArrayList<User>;
        ServerSocket server = null;
        
        try {
            // Create a ServerSocket to listen for incoming connections on port 8000
            server = new ServerSocket(8000);
            server.setReuseAddress(true);
            System.out.println("ServerSocket awaiting connections8...");
            
            while (true) {
                Socket client = server.accept();
                
                System.out.println("New client connected" + client.getInetAddress().getHostAddress());
                System.out.println("Connection from " + client + "!");
                
                // assigns the client handler to a client socket
                ClientHandler clientSock = new ClientHandler(client);
                
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private final Socket clientSocket;
    private boolean loggedIn = false; // checks if client has logged in
    private boolean endConnection = false; // checks if user wants to close connection
    private Queue<Message> msgQueue; // queue for messages 
    
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        msgQueue = new LinkedList<Message>();
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
