import java.util.Date;
import java.io.Serializable;

public class Message implements Serializable {
	private int counter;
	private int id;
	private MessageType type;
	private String content;
	private User owner;
	private Date created;
	private Boolean editedFlag;
	private Date dateEdited;
	private int messageThreadID = -1;
	
	public Message(MessageType type) {
		this.type = type;
	}
	
	public Message(User owner, String content, MessageType type) {
		this.owner = owner;
		this.content = content;
		this.type = type;
	}
	
	public Message(User owner, String content, int threadID) {
        this.owner = owner;
        this.messageThreadID = threadID;
        this.type = MessageType.NEW_TEXT;
    }
	
	public Message(User owner, String content, MessageThread chat) {
		this.owner = owner;
		this.content = content;
	}

	public MessageType getType() {
		return this.type;
	}
	
	public String getContent() {
		return content;
	}
	
	public User getOwner() { //Owner is the Sender
		return owner;
	}
	
	public Date getCreationDate() {
		return created;
	}
	
	public int getMessageThreadID() {
		return messageThreadID;
	}
	
	public void setMessageThreadID(int givenID) {
		if(messageThreadID == -1) {
			messageThreadID = givenID;
		}
	}
	
}
