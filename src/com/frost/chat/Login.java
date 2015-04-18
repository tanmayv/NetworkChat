package com.frost.chat;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {



	private static final long serialVersionUID = -2980921534003870323L;
	private JPanel contentPane;
	private JTextField txtName;
	private JLabel lblIpAddress;
	private JTextField txtIp;
	private JLabel lblPort;
	private JTextField txtPort;
	

	public Login() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,380);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtName = new JTextField();
		txtName.setText("Tanmay");
		txtName.setBounds(70, 51, 154, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(133, 26, 27, 14);
		contentPane.add(lblName);
		
		lblIpAddress = new JLabel("IP Address");
		lblIpAddress.setBounds(121, 82, 52, 14);
		contentPane.add(lblIpAddress);
		
		txtIp = new JTextField();
		txtIp.setText("localhost");
		txtIp.setColumns(10);
		txtIp.setBounds(70, 107, 154, 20);
		contentPane.add(txtIp);
		
		lblPort = new JLabel("PORT");
		lblPort.setBounds(131, 139, 32, 14);
		contentPane.add(lblPort);
		
		txtPort = new JTextField();
		txtPort.setText("8080");
		txtPort.setColumns(10);
		txtPort.setBounds(70, 164, 154, 20);
		contentPane.add(txtPort);
		
		JButton btnLogin = new JButton("Login");
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				String address = txtIp.getText();
				
				int port = Integer.parseInt(txtPort.getText());
				login(name, address, port);
			}
		});
		btnLogin.setBounds(102, 295, 89, 23);
		
		contentPane.add(btnLogin);
	}
	
	/**
	 * Login Stuff here
	 */
	
	private void login(String name, String address, int port){
		new ClientWindow(name, address, port);
		dispose();
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
