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
	
	//default constructor
	public Message() {
		this.type = MessageType.NEW_TEXT;
		this.content = ("");
		this.owner = null;
		this.created = new Date();
		this.editedFlag = false;
		this.dateEdited = null;
	}

	public Message(MessageType MsgType) {
		this.type = MsgType;
		this.content = ("");
		this.setCreatedDate(new Date());
	}

	public Message(User owner, String content, MessageType type) {
		this.owner = owner;
		this.content = content;
		this.type = type;
		this.setCreatedDate(new Date());
	}

	public Message(User owner, MessageType type) {
		this.setOwner(owner);
		this.setType(type);
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

	public void setType(MessageType MsgType) {
		this.type = MsgType;
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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Date getCreatedDate() {
		return created;
	}

	public void setCreatedDate(Date created) {
		this.created = created;
	}

	public Boolean getEditedFlag() {
		return editedFlag;
	}

	public void setEditedFlag(Boolean editedFlag) {
		this.editedFlag = editedFlag;
	}

	public Date getDateEdited() {
		return dateEdited;
	}

	public void setDateEdited(Date dateEdited) {
		this.dateEdited = dateEdited;
	}

	public int getMessageThreadID() {
		return messageThreadID;
	}

	public void setMessageThreadID(int givenID) {
		if (messageThreadID == -1) {
			messageThreadID = givenID;
		}
	}

}
