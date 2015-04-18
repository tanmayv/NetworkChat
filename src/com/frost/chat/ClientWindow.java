package com.frost.chat;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ClientWindow extends JFrame implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtMessage;
	private JButton btnNewButton;
	private JTextArea txtHistory;
	private JPanel contentPane;
	
	private Client client;
	private Thread listen,run;
	
	private boolean running = false;

	
	public ClientWindow(String name, String address, int port) {
		
		setTitle("Client Window");
		
		client = new Client(name, address, port);
		createWindow();
		console("Attempting a connection to "+ address + ":" + port + ", user : "+name);
		boolean connect = client.openConnection(address, port);
		
		if(!connect){
			System.err.println("Connection Failed!");
			console("Connection Failed!");
		}
		String message = "/c/"+name + "/e/";
		client.send(message.getBytes());
		message = name + " connected from " + address + ":" + port;
		client.send(message.getBytes());
		running = true;
		run = new Thread(this,"run");
		run.start();
	}
	
	public void run(){
		
		listen();
		
	}
	
	public void createWindow(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(880,550);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{16,827,30,7};
		gbl_contentPane.rowHeights = new int[]{35,475,40};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0};
		contentPane.setLayout(gbl_contentPane);
		
		txtHistory = new JTextArea();
		txtHistory.setEditable(false);
		JScrollPane scroll = new JScrollPane(txtHistory);
		
		GridBagConstraints gbc_txtrHistory = new GridBagConstraints();
		gbc_txtrHistory.insets = new Insets(10, 10, 5, 5);
		gbc_txtrHistory.fill = GridBagConstraints.BOTH;
		gbc_txtrHistory.gridx = 0;
		gbc_txtrHistory.gridy = 0;
		gbc_txtrHistory.gridwidth = 3;
		gbc_txtrHistory.gridheight = 2;
		
		contentPane.add(scroll, gbc_txtrHistory);
		
		txtMessage = new JTextField();
		txtMessage.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					sendButtonClicked();
			}
		});
		GridBagConstraints gbc_txtMessage = new GridBagConstraints();
		gbc_txtMessage.insets = new Insets(0, 10, 0, 5);
		gbc_txtMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMessage.gridx = 0;
		gbc_txtMessage.gridy = 2;
		gbc_txtMessage.gridwidth = 2;
		gbc_txtMessage.gridheight = 2;
		contentPane.add(txtMessage, gbc_txtMessage);
		txtMessage.setColumns(10);
		
		btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendButtonClicked();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 2;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		setVisible(true);
		txtMessage.requestFocusInWindow();
	}

	public void console(String message){
		txtHistory.append(message + "\n\r");
		txtHistory.setCaretPosition(txtHistory.getDocument().getLength());
	}
	
	private void sendButtonClicked(){
		if(!txtMessage.getText().equals("")){
			String message = client.getName() + " : "+txtMessage.getText(); 
			//console(message);
			message = "/m/" + message;
			client.send(message.getBytes());
			txtMessage.setText("");
		}
		txtMessage.requestFocusInWindow();
	}
	
	public void listen(){
		listen = new Thread("Listen"){
			public void run(){
				while(running){
					String message = client.receive();
					if(message.startsWith("/c/")){
						
						System.out.println(message);
						client.setID( Integer.parseInt(message.split("/c/|/e/")[1]));
						console("Successfully connected to Server! ID :" + client.getID() );
					}
					else if (message.startsWith("/m/")){
						console(message.split("/m/|/e/")[1]);
					}	
				}
			}
		};
		listen.start();
	}

}
