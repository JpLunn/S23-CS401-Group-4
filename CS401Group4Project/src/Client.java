import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Client {
	static private User currentUser;
	//private ListArray<Notifications> notifications;
	static private boolean sessionActive = true;
	static private Queue<Message> messageQueue = new LinkedList<>();
	static private boolean loggedIn = false;
	
	
	
	
    public static void main(String[] args) {
        try {
        	// Create a socket to connect to the server on port 8000
            Socket socket = new Socket("localhost", 8000);
            
            // Get the input and output streams for the socket
//            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            // Send a message to the server
//            out.println("Hello from the client");
            try {
                OutputStream oStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(oStream);
                InputStream iStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(iStream);
                Scanner sc= new Scanner(System.in); //System.in is a standard input stream.
                
                
                currentUser = new User();
                System.out.println("Enter password");
                currentUser.setPassword(sc.nextLine());
                System.out.println("Enter username");
                currentUser.setUsername(sc.nextLine());
                
                Message loginMsg = new Message(currentUser, MessageType.LOGIN);
//                System.out.println(currentUser.getPassword());
//                System.out.println(currentUser.getUsername());
                objectOutputStream.writeObject(loginMsg);
                
                while(sessionActive) {
                    
                    if(loggedIn == false) {
                        
                        Message inMessage = (Message) objectInputStream.readObject();
                        if(inMessage.getType().equals(MessageType.VALID_LOGIN)) {
                            loggedIn = true;
                            currentUser = inMessage.getOwner();
                        } else {
                            System.out.println("Invalid Login");
                        }
                    } 
                    
                    if(loggedIn == true) {
                        System.out.println("Enter a message to send");
                        
                        
                        String userMessage = sc.nextLine();
                        if(userMessage.compareToIgnoreCase("refresh")!=0) {
                            Message outMessage = new Message(currentUser,userMessage,1);
                            
//                          System.out.println(outMessage.getContent());
                          
                          objectOutputStream.writeObject(outMessage);
//                          objectOutputStream.flush();
//                          Message newMsg = new Message();
                        }
                        Message newMsg= (Message) objectInputStream.readObject();
//                      objectInputStream.reset();
                      
                      
                        System.out.println(newMsg.getContent());

                    }
                }
                
            }catch(IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // Read the server's response
//            String response = in.readLine();
//            System.out.println("Received from server: " + response);
            
//            GUI clientGUI = new GUI(socket);
            // Close the input, output, and socket
//            in.close();
//            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    
    public void setUser(User currentUser) {
    	this.currentUser = currentUser;
    }
    
    public User getCurrentUser() {
    	return currentUser;
    }
    
    public void setSessionActive(boolean SessionActive) {
    	this.sessionActive = SessionActive;
    }
    
    public boolean getSessionActive() {
    	return sessionActive;
    }
    
    public void setMessageQueue(Queue<Message> MessageQueue) {
    	this.messageQueue = MessageQueue;
    }
    
    public Queue<Message> getMessageQueue() {
    	return messageQueue;
    }
}
