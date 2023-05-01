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
    static public List<ClientHandler> clients = new ArrayList<>();
    static private int MAXMSGLEN = 100;
    
    public static void main(String[] args) {
                User testUser = new User();
                User testUser2 = new User();
        
        		testUser.setUserType(UserType.ADMIN);
        		testUser.setFirstName("John");
        		testUser.setLastName("Doe");
        		testUser.setUsername("JDoe");
        		testUser.setPassword("Testing");
        		testUser.setBlockedFlag(false);
        		testUser.setUserState(UserState.OFFLINE);
        		testUser.setThreadList(new ArrayList<MessageThread>());

                usersList.add(testUser);
                testUser2 = new User();
        		testUser2.setUserType(UserType.DEFAULT);
        		testUser2.setFirstName("Henry");
        		testUser2.setLastName("Bnafa");
        		testUser2.setUsername("HBnafa");
        		testUser2.setPassword("Testing2");
        		testUser2.setBlockedFlag(false);
        		testUser2.setUserState(UserState.OFFLINE);
        		testUser2.setThreadList(new ArrayList<MessageThread>());
        		usersList.add(testUser);
                saveUsers();
                
                ArrayList<User> testList = new ArrayList<User>();
                ArrayList<Message> msgTestList = new ArrayList<Message>();
                testList.add(testUser2);
                testList.add(testUser);
                
                for(int i=0; i<10; i++) {
                    Message msg = new Message(testUser, "message #"+ i, MessageType.NEW_TEXT, 1 );
                    msgTestList.add(msg);
                    msg = new Message(testUser2, "message #"+ i, MessageType.NEW_TEXT, 1 );
                    msgTestList.add(msg);
                }
                MessageThread msgThread = new MessageThread(testList,1, msgTestList);
                messageThreads.add(msgThread);
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
    
    public static void sendMessageThread(Message newMessage) {
//        System.out.println("message content 2nd stop: " + newMessage.getContent());
        MessageThread newThread = checkMessageThread(newMessage);
//        System.out.println("Message Thread ID is: "+newThread.getID());
        for(ClientHandler client : clients) {
            for(int i=0; i<newThread.getParticipants().size(); i++) {
                if(client.user ==  newThread.getParticipants().get(i));
                
//                System.out.println("client: " + client);
//                System.out.println("user1: " + client.user);
//                System.out.println("user2: " + newThread.getParticipants().get(i));
                // get the outputstream of client
                    client.sendMessage(newMessage);
            }
        }

    }
    
    public static Message checkLogin(Message msg) {
        
        for(User user: usersList) {
            if(user.getUsername().contentEquals(msg.getOwner().getUsername())) {
            	if(user.getPassword().contentEquals(msg.getOwner().getPassword())) {
            		return new Message(user, MessageType.VALID_LOGIN);
            	}
            } 
        }
        return new Message(MessageType.INVALID_LOGIN);
    }
    
    // Finds the threadID for the Message in Array list.
    public static MessageThread checkMessageThread(Message msg) {
    	int threadID = msg.getMessageThreadID();
//    	System.out.println( msg.getMessageThreadID());
//    	System.out.println("Message threads size: " +messageThreads.size());
    	for(int i=0; i<messageThreads.size(); i++) {
    	    MessageThread thread = messageThreads.get(i);
    	    if(threadID == messageThreads.get(i).getID()) {
    	        return thread;
    	    }
    	}    	
    	return null;
    }
    
}

class ClientHandler implements Runnable {
    public final Socket clientSocket;
    private boolean loggedIn = false; // checks if client has logged in
    private boolean endConnection = false; // checks if user wants to close connection
    private Queue<Message> msgQueue; // queue for messages 
    public User user;
    
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        msgQueue = new LinkedList<Message>();
        //user.setSocket(clientSocket);
    }
    
    public void sendMessage(Message newMessage) {
        try {
//            System.out.println("message content 3rd stop: "+ newMessage.getContent());
            // get the outputstream of client
//            System.out.println();
            OutputStream outputStream = clientSocket.getOutputStream();
            
            // Create a ObjectOutpuitStream so we can send objects to clients
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            // get the input stream from the connected socket
            // create a ObjectInputStream so we can read data from it.

//            System.out.println(newMessage.getType()+ "-----"+newMessage.getContent()+"-----" + newMessage.getMessageThreadID());
            objectOutputStream.writeObject(newMessage);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
            
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

                if(!msgQueue.isEmpty()) {
                    Message msg = msgQueue.poll(); 
//                    System.out.println("message content 0st stop: " + msg.getContent());
//                    System.out.println("message type 0st stop: " + msg.getContent());
                    
//                    if the message type is login log the user in
                    if(!loggedIn) {
                        if(msg.getType()==MessageType.LOGIN) {
                            Message loginResponseMsg = Server.checkLogin(msg);
                            if(loginResponseMsg.getType()==MessageType.VALID_LOGIN) {
                                this.loggedIn = true;
                                this.user = loginResponseMsg.getOwner();
                                Server.clients.add(this);
                                objectOutputStream.writeObject(loginResponseMsg);
                                
                            } else {
                                objectOutputStream.writeObject(loginResponseMsg);
                            }
                        }
                    }
                    
                    
                    
                    // else it checks if the user is logged in
                    else if(loggedIn) {
                        // checks the message type of the message
                        switch (msg.getType()) {
                        
                        case LOGOUT: // if logout message it logs the user out and returns a success logout message and closes the connection
                            objectOutputStream.writeObject(new Message(MessageType.LOGOUT));
                            
                            // Show that client has logged out
                            System.out.println("Client successfully logged out");
                            
                            loggedIn = false;
                            endConnection = true;
                            
                            // Close sockets
                            objectOutputStream.close();
                            inputStream.close();
                            
                            break;
                        case NEW_TEXT: // if text message it returns a new message with the data capitalized.
//                            System.out.println("message content 1st stop: " + msg.getContent());
//                            System.out.println("message type 1st stop: " + msg.getContent());
                            Server.sendMessageThread(msg);
                            break;
						default:
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
