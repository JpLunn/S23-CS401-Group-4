import java.util.Date;

public class Message {
	private int counter;
	private int id;
	private MessageType type;
	private String content;
	private User owner;
	private Date created;
	private Boolean editedFlag;
	private Date dateEdited;
	private int messageThreadID;
	
	public Message(MessageType type) {
		this.type = type;
	}
	
	public Message(User owner, String content, MessageType type) {
		this.owner = owner;
		this.content = content;
		this.type = type;
	}
	
	public Message(User owner, String content, MessageThread chat) {
		this.owner = owner;
		this.content = content;
	}
}
