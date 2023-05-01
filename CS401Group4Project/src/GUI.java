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

import java.util.ArrayList;

public class GUI implements ClientInterface{
	
	
	private JFrame frame;
	private JTextField newMessageField;
	private User activeUser= null;
	
	
	public GUI(User loginUser) {
		activeUser = loginUser;
	}
	
	public GUI() {
       	EventQueue.invokeLater(new Runnable() {
   			public void run() {
   				try {
   					GUI window = new GUI();
   					window.frame.setVisible(true);
   				} catch (Exception e) {
   					e.printStackTrace();
   				}
   			}
   		});
		createWindow();
	}
	
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
	
	private void createMessageLoc() {
		
	}
	
	public void login() {
		
	}
	
	private void logout() {
		
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
		frame = new JFrame();
		frame.setBounds(100, 100, 490, 333);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu File = new JMenu("File");
		menuBar.add(File);
		
		JMenuItem CreateNewMessage = new JMenuItem("New Message");
		File.add(CreateNewMessage);
		
		JMenuItem LoginButton = new JMenuItem("Login");
		File.add(LoginButton);
		
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
		for(int i = 0; i < activeUser.getThreadList().size(); i++) {
			if(activeUser.getThreadList().get(i).equals(null)) {
				break;
			}
			temp.add(activeUser.getThreadList().get(i).getParticipants().get(1));
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
		
		
		
		
		LoginButton.addActionListener(new ActionListener() {
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
}