import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

public class User implements Serializable{
    private static int counter =0;
    private int id;
    private Socket clientSocket;
    private UserType userType;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean blockedFlag;
    private UserState userState;
    private ArrayList<MessageThread> threadList = new ArrayList<MessageThread>();

    // User Constructor for new user
    public User() {
        this.id = ++counter;
    }
    
    public User(UserType userType, String first, String last, String username, String password, boolean blocked, UserState userState, ArrayList<MessageThread> threadList) {
        this.id = ++counter;
        this.userType = userType;
        this.firstName = first;
        this.lastName = last;
        this.username = username;
        this.password = password;
        this.blockedFlag = blocked;
        this.userState = userState;
        this.threadList = threadList;
    }
    
    // User Constructor for loading User
    public User(int id,UserType userType, String first, String last, String username, String password, boolean blocked, UserState userState, ArrayList<MessageThread> threadList) {
        this.id = id;
        this.userType = userType;
        this.firstName = first;
        this.lastName = last;
        this.username = username;
        this.password = password;
        this.blockedFlag = blocked;
        this.userState = userState;
        this.threadList = threadList;
    }
    
//    Setters
    public void setID(int id) {
        this.id = id;
    }
    
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    
    public void setFirstName(String first) {
        this.firstName = first;
    }
    
    public void setLastName(String last) {
        this.lastName = last;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public void setBlockedFlag(boolean blocked) {
        this.blockedFlag = blocked;
    }
    
    public void setThreadList(ArrayList<MessageThread> threadList) {
        this.threadList = threadList;
    } 
    
    public void setCounter(int counter) {
        User.counter = counter;
    }
    
//    Getters
    public int getID() {
        return this.id;
    }
    
    public UserType getUserType() {
        return this.userType;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;    
    }
    
    public boolean getBlockedFlag() {
        return this.blockedFlag;
    }
    
    public ArrayList<MessageThread> getThreadList() {
        return this.threadList;
    }
    
    public int getCounter() {
        return User.counter;
    }
    
    // Change username and pass functions
    public void changeUsername(String username) {
    	this.username = username;
    }
    
    public void changePassword(String password) {
    	this.password = password;
    }

	public void setUserState(UserState userState) {
		this.userState = userState;
		
	}
	
	public String toString() {
    	return id + "|" + userType + "|" + firstName + "|" + lastName + "|" + username + "|" + password + "|" +
    			blockedFlag + "|" + userState;
    }
	
	public void setSocket (Socket clientSocket) {
	    this.clientSocket = clientSocket;
	}
	public Socket getSocket () {
        return this.clientSocket;
    }
	
}
