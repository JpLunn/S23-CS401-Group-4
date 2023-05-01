import java.util.Date;
import java.io.Serializable;

public class Message implements Serializable {
	private int counter;
	private int id;
	private MessageType type;
	private String MsgType; //Testing out msgType with string instead of class
	private String content;
	private User owner;
	private Date created;
	private Boolean editedFlag;
	private Date dateEdited;
	private int messageThreadID = -1;
	
	//Constructor
	public Message() {
		this.setType("NONE");
		this.content = ("");
		this.setOwner(new User());
		this.setCreatedDate(new Date());
		
	}
	public Message(String MsgType) {
		this.MsgType = MsgType;
		this.content = ("");
		this.setOwner(new User());
		this.setCreatedDate(new Date());
	}
	
	public Message(User owner, String content, String MsgType) {
		this.setOwner(owner);
		this.setContent(content);
		this.setType(MsgType);
		this.setCreatedDate(new Date());
	}
	
	public Message(User owner, String content, int threadID) {
        this.owner = owner;
        this.messageThreadID = Integer.valueOf(threadID);
        this.type = MessageType.NEW_TEXT;
        this.setCreatedDate(new Date());
    }
	
	public Message(User owner, String content, MessageThread chat) {
		this.owner = owner;
		this.content = content;
		this.setCreatedDate(new Date());
	}
	
	public void setType(String MsgType) {
		this.MsgType = MsgType;
	}

	public MessageType getType() {
		return this.type;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public User getOwner() { //Owner is the Sender
		return owner;
	}
	
	public void setCreatedDate(Date dateEdited) {
		this.dateEdited = dateEdited;
		
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
