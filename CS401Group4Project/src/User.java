import java.util.ArrayList;

public class User {
    private static int counter =0;
    private UserType userType;
    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private boolean blockedFlag;
    private UserState userState;
    private ArrayList<MessageThread> threadList = new ArrayList<MessageThread>();

    // User Constructor
    public User() {
        
    }


}
