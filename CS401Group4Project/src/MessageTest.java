import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class MessageTest {

//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}
	
	@Test
	void ConstructorOneTest() {
		ArrayList<MessageThread> threads = new ArrayList<MessageThread>();
		User me = new User(UserType.REGULAR,"Turk", "Turkleton", "TT360", "love", false, UserState.ONLINE, threads);
		Message test = new Message(me,"Hello world", MessageType.NEW_TEXT);
		
		assertTrue(test.getOwner() != null && test.getContent() != null && test.getType() != null);
	}
	
//	@Test
//	void ConstructorTwoTest() {
//		ArrayList<MessageThread> threads = new ArrayList<MessageThread>();
//		User me = new User(UserType.REGULAR,"Turk", "Turkleton", "TT360", "love", false, UserState.ONLINE, threads);
//		Message test = new Message(me, "hello world", 3);
//		
//		assertTrue(test.getOwner() != null && test.getContent() != null);
//	}
	
	@Test
	void getTypeTest() {
		ArrayList<MessageThread> threads = new ArrayList<MessageThread>();
		User me = new User(UserType.REGULAR,"Turk", "Turkleton", "TT360", "love", false, UserState.ONLINE, threads);
		Message test = new Message(me, "hello world", MessageType.NEW_TEXT);
		assertTrue(!test.getType().equals(null));
	}
	
	@Test 
	void getContentTest() {
		ArrayList<MessageThread> threads = new ArrayList<MessageThread>();
		User me = new User(UserType.REGULAR,"Turk", "Turkleton", "TT360", "love", false, UserState.ONLINE, threads);
		Message test = new Message(me, "hello world", MessageType.NEW_TEXT);
		assertTrue(test.getContent() != null);
	}

	@Test
	void getOwnerTest() {
		ArrayList<MessageThread> threads = new ArrayList<MessageThread>();
		User me = new User(UserType.REGULAR,"Turk", "Turkleton", "TT360", "love", false, UserState.ONLINE, threads);
		Message test = new Message(me, "hello world", MessageType.NEW_TEXT);
		assertTrue(test.getOwner() != null);
	}
	
	
}
