import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import java.net.*;
import java.util.*;
import java.io.*;

public class GUI implements ClientInterface{
	
	
	private static JFrame frame;
	private JTextField newMessageField;
	private User activeUser= null;
	private boolean loggedIN = false;
	
	public GUI() {
		
	}
	
	public GUI(User loginUser) {
		activeUser = loginUser;
		this.frame = new JFrame(activeUser.getFirstName() + " " + activeUser.getLastName());
		this.frame.setVisible(true);
		this.createWindow();
	}
	
	/*
	 * Initial Testing GUI Constructor - Depreciated
	 */
//	public GUI(Socket connectedSocket) {
//		Message inMessage = null;
//       	try {
//       		OutputStream oStream = connectedSocket.getOutputStream();
//       		ObjectOutputStream objOStream = new ObjectOutputStream(oStream);
//       		InputStream iStream = connectedSocket.getInputStream();
//       		ObjectInputStream objIStream = new ObjectInputStream(iStream);
//       		while(loggedIN == false) {
//       			inMessage = login();
//       			if(inMessage == null) {return;}
//       			objOStream.writeObject(inMessage);
//       			inMessage = (Message) objIStream.readObject();
//       			if(inMessage.getType().equals(MessageType.VALID_LOGIN)) {
//       				loggedIN = true;
//       				activeUser = inMessage.getOwner();
//       			}
//       			else {
//       				JOptionPane.showMessageDialog(frame, "Invalid Login");
//       			}
//       		}
//       	}catch(IOException e) {
//       		e.printStackTrace();
//       	} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		try {
//       		this.frame = new JFrame(activeUser.getFirstName() + " " + activeUser.getLastName());
//   			this.frame.setVisible(true);
//   		} catch (Exception e) {
//   			e.printStackTrace();
//   		}
//   	createWindow();
//	}
	
	private void createMessage(MessageType type, String content) {
		// Create instance of the message class
		Message currMessage = new Message(activeUser, content, type);
		
		// Handle different message types
		if(type.equals(MessageType.LOGIN)) {
			login();
		} else if(type.equals(MessageType.LOGOUT)) {
			logout();
		} // etc.
	}
	
	private void createMessageLog() {
		
	}
	
	public static Message login() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));

	    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
	    label.add(new JLabel("Username", SwingConstants.RIGHT));
	    label.add(new JLabel("Password", SwingConstants.RIGHT));
	    panel.add(label, BorderLayout.WEST);

	    JPanel control = new JPanel(new GridLayout(0, 1, 2, 2));
	    JTextField username = new JTextField();
	    control.add(username);
	    JTextField password = new JTextField();
	    control.add(password);
	    panel.add(control, BorderLayout.CENTER);

	    int temp = JOptionPane.showConfirmDialog(frame, panel, "login", JOptionPane.OK_CANCEL_OPTION);
	    if(temp == JOptionPane.CANCEL_OPTION) {
	    	return null;
	    }
	    User tempUser = new User();
	    tempUser.setUsername(username.getText());
	    tempUser.setPassword(password.getText());
	    Message loginMessage = new Message(tempUser, "Login", MessageType.LOGIN);
	    return loginMessage;
	    
	}
	
	private void logout() {
		JPanel panel = new JPanel();
		int temp = JOptionPane.showConfirmDialog(frame, panel , "Would you like to Logout", JOptionPane.YES_NO_OPTION)
	}
	
	public void processCommands() {
		
	}
	
	private void sendMessage(User reciever, String textData) {
		
	}
	
	private void viewOnlineUsers() {
		
	}
	
	private void viewITLogs() {
		
	}
	
	private void viewMessages() {
		
	}
	
	private void createWindow() {
		//frame = new JFrame();
		frame.setBounds(100, 100, 650, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu File = new JMenu("File");
		menuBar.add(File);
		
		JMenuItem CreateNewMessage = new JMenuItem("New Message");
		File.add(CreateNewMessage);
		
//		JMenuItem LoginButton = new JMenuItem("Login");
//		File.add(LoginButton);
		
		JMenuItem LogoutMenu = new JMenuItem("Logout");
		File.add(LogoutMenu);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("     User List     ");
		panel.add(lblNewLabel, BorderLayout.NORTH);
		
		JList list = new JList();
		panel.add(list);
		ArrayList<User> temp = new ArrayList<User>();
		if(activeUser != null) {
			for(int i = 0; i < activeUser.getThreadList().size(); i++) {
				if(activeUser.getThreadList().get(i).equals(null)) {
					break;
				}
				temp.add(activeUser.getThreadList().get(i).getParticipants().get(1));
			}
		}

		list.setModel(new AbstractListModel() {
			public int getSize() {
				return temp.size();
			}
			public Object getElementAt(int index) {
				return temp.get(index);
			}
		});
		list.setVisibleRowCount(15);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		newMessageField = new JTextField();
		panel_1.add(newMessageField);
		newMessageField.setColumns(50);
		
		JButton sendMessageButton = new JButton("Send");
		panel_1.add(sendMessageButton);
		
		TextArea textArea = new TextArea();
		frame.getContentPane().add(textArea, BorderLayout.CENTER);
		
		
		
		
		LogoutMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent mA) {
				login();
			}});
		CreateNewMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent mA) {
				createMessage();
			}});
		
		
		
		sendMessageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent mA) {
				if(!newMessageField.getText().isBlank()) {
					sendMessage((User) list.getSelectedValue(), newMessageField.getText());
				}
				return;
			}
		});
	}
	public JFrame getFrame() {
		return this.frame;
	}
}