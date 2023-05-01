import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

class MessageThreadTest {

//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}
	
	@Test
	void constructorTest() {
		MessageThread messages = new MessageThread();
		
		assertTrue(messages.toString() != null);	//Tests if the object is created
	}
	
	@Test
	void idTest() {
		MessageThread messages = new MessageThread();
		messages.setID(125);
		assertTrue(messages.getID() == 125);
	}
	
	@Test 
	void displayNameTest() {
		MessageThread messages = new MessageThread();
		messages.setDisplayName("Bubba");
		assertTrue(messages.getDisplayName().equals("Bubba"));
	}
	
	@Test
	void maxMessageTest() {
		MessageThread.setMAXMESSAGE(160);
		assertTrue(MessageThread.getMAXMESSAGE() == 160);
	}
}
