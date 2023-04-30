import java.util.ArrayList;
import java.util.Date;

public class MessageThread {
	private int ID;
	private ArrayList<Message> messageList;
	private ArrayList<User> participants;
	private Date created;
	private Date lastUpdated;
	private Date lastServerUpdateSent;
	private String displayName;
	private static int MAXMESSAGE = 100;
	
	public MessageThread() {
		// Initialize list of messages
		messageList = new ArrayList<>();
				
		// Initialize list of participants
		participants = new ArrayList<>();
	}
	
	public void addMessage(User givenUser, Message givenMessage) {
		if(participants.contains(givenUser)) {
			messageList.add(givenMessage);
		}
		else {
			return;
		}
		return;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public ArrayList<Message> getMessageList() {
		return messageList;
	}

	public void setMessageList(ArrayList<Message> messageList) {
		this.messageList = messageList;
	}

	public ArrayList<User> getParticipants() {
		return participants;
	}

	public void setParticipants(ArrayList<User> participants) {
		this.participants = participants;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Date getLastServerUpdateSent() {
		return lastServerUpdateSent;
	}

	public void setLastServerUpdateSent(Date lastServerUpdateSent) {
		this.lastServerUpdateSent = lastServerUpdateSent;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public static int getMAXMESSAGE() {
		return MAXMESSAGE;
	}

	public static void setMAXMESSAGE(int mAXMESSAGE) {
		MAXMESSAGE = mAXMESSAGE;
	}
}
