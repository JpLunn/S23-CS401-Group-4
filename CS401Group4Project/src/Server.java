import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Server {
    static private ArrayList<User> usersList = new ArrayList<User>();
    static private ArrayList<MessageThread>  messageThreads = new ArrayList<MessageThread>();
    static private int MAXMSGLEN = 100;
    
    public static void main(String[] args) {
        User testUser = new User();
        		testUser.setUserType(UserType.ADMIN);
        		testUser.setFirstName("John");
        		testUser.setLastName("Doe");
        		testUser.setUsername("JDoe");
        		testUser.setPassword("Testing");
        		testUser.setBlockedFlag(false);
        		testUser.setUserState(UserState.OFFLINE);
        		testUser.setThreadList(new ArrayList<MessageThread>());
        usersList.add(testUser);
        saveUsers();
        
        ServerSocket server = null;
        
        try {
            // Create a ServerSocket to listen for incoming connections on port 8000
            server = new ServerSocket(8000);
            server.setReuseAddress(true);
            System.out.println("ServerSocket awaiting connections...");
            
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
        saveMessages();
        saveMessageThreads();
    }
    
    public static void startup() {
        loadUsers();
        loadMessages();
        loadMessageThread();        
        
    }
    
    public static void loadUsers() {
    	File userFile = new File("Users.txt");
    	try {
    		Scanner fScanner = new Scanner(userFile);
    		while(fScanner.hasNextLine()) {
    			String data = fScanner.nextLine();
    			String[] dataLoad = data.split();
    		}
    	}catch(Exception e) {
    		return;
    	}
    	
    }
    
    public static void saveUsers() {
        try {
            FileWriter out = new FileWriter(new File("Users.txt"));
            String outputString = "";
            
            for(int i=0; i<usersList.size(); i++) {
                outputString = outputString + usersList.get(i).toString() + "\n";
            }
            out.write(outputString);
            out.close();
        }
        catch (IOException e){
            return;
        }
    }
    
    public static User findUser(String userString) {
        for(User user: usersList) {
            if(user.getID() == Integer.valueOf(userString))
                return user;
        }
        return null;
    }
    
   public static void loadMessages() {
        // Load messages from file
        try {
            File file = new File("Messages.txt");
            if (!file.exists()) {
                file.createNewFile();
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                String[] splitLine = line.split(":::");
                int threadID = Integer.parseInt(splitLine[0]);
                String sender = splitLine[1];
                User owner = findUser(sender);
                String messageText = splitLine[2];
                Message message = new Message(owner, messageText, threadID);
                for (MessageThread thread : messageThreads) {
                    if (thread.getID() == threadID) {
                        thread.addMessage(message);
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveMessages() {
        // Save messages to file
        try {
            FileWriter out = new FileWriter(new File("Messages.txt"), true);
            for (MessageThread thread : messageThreads) {
                ArrayList<Message> messages = thread.getMessageList();
                for (Message message : messages) {
                    out.write(thread.getID() + ":::" + message.getOwner() + ":::" + message.getContent() + "\n");
                }
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void loadMessageThread() {
//        loads all message threads from file and saves to messageThread arraylist
    }
    public static void saveMessageThreads() {
        
    }
    
    public static Message checkLogin(Message msg) {
        
        for(User user: usersList) {
            if(user == msg.getOwner())
                return new Message(user, MessageType.VALID_LOGIN);
        }
        return new Message(MessageType.INVALID_LOGIN);
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
                if(newMsg.getType().equals(MessageType.LOGIN)) {
                	System.out.println("LoginAttempted");
                }
                msgQueue.add(newMsg);
                
                if(!msgQueue.isEmpty()) {
                    Message msg = msgQueue.poll(); 
                    
//                    if the message type is login log the user in
                    if(msg.getType()==MessageType.LOGIN) {
                        if(!loggedIn) {
                            checkLogin(msg);
                            this.loggedIn = true;   
                            objectOutputStream.writeObject(new Message(MessageType.Login,"Success",""));
                        }
                    }
                    
                    // else it checks if the user is logged in
                    else if(loggedIn) {
                        // checks the message type of the message
                        switch (msg.getType()) {
                        
                        case Logout: // if logout message it logs the user out and returns a success logout message and closes the connection
                            objectOutputStream.writeObject(new Message(MessageType.Logout,"Success",""));
                            loggedIn = false;
                            endConnection = true;
                            break;
                        case Text: // if text message it returns a new message with the data capitalized.
                            objectOutputStream.writeObject(new Message(MessageType.Text,"Success",msg.getText().toUpperCase()));
                            break;
                        }
                    } 
                }
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
