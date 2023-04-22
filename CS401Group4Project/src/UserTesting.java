import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class UserTesting {
    
    
//    Testing User ID incrementation
    @Test
    public void testIncrementingID() {
        ArrayList<User> userList = new ArrayList<User>();
        for(int i=1; i<11; i++) {
            userList.add(new User()); 
            assertEquals(userList.get(i-1).getID(), i);
        }
    }
    
}
