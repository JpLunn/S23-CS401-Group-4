import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Server {
    static private ArrayList<User> userList = new ArrayList<User>();
    static private ArrayList<MessageThread>  messageThreads = new ArrayList<MessageThread>();
    static private int MAXMSGLEN = 100;
    
    public static void main(String[] args) {
        
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
    
    public static void save() {
        saveUsers();
        saveMessageThreads();
    }
    
    public static void startup() {
        loadUsers();
        loadMessageThread();        
        
    }
    
    public static void loadUsers() {
//      Write function to read users from file and save to userList arraylist
    }
    
    public static void saveUsers() {
        
    }
    public static void loadMessageThread() {
//        loads all message threads from file and saves to messageThread arraylist
    }
    public static void saveMessageThreads() {
        
    }
    
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private boolean loggedIn = false; // checks if client has logged in
    private boolean endConnection = false; // checks if user wants to close connection
    private Queue<Message> msgQueue; // queue for messages 
    private User user;
    
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        msgQueue = new LinkedList<Message>();
    }
    
    public void run() {
        
        
        try {
            
            // get the outputstream of client
            OutputStream outputStream = clientSocket.getOutputStream();
            
            // Create a ObjectOutpuitStream so we can send objects to clients
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            

            // get the input stream from the connected socket
            InputStream inputStream = clientSocket.getInputStream();

            // create a ObjectInputStream so we can read data from it.
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            // runs this loop while there is a connection
            while(!endConnection) {
             // reads adds any incoming messages to the queue
                Message newMsg = (Message) objectInputStream.readObject();
                msgQueue.add(newMsg);
                
                
            }
           
         // closes the socket connection
            clientSocket.close();
            
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
