package com.frost.chat.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable{
	
	private List<ServerClient> clients = new ArrayList<ServerClient>();
	
	private int port;
	private DatagramSocket socket;
	private Thread run, manage, send, receive;
	private boolean running = false;
	
	Server(int port){
		this.port = port;
		try {
			InetAddress address;
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		run = new Thread(this, "Server");
		run.start();
	}	
	
	public void run(){
		running = true;
		System.out.println("Server Running Ehhl!");
		//manageClients();
		receive();
	}
	
	
	private void manageClients(){		//send pings to see if client is connected or not.
		manage = new Thread("Manage"){
			public void run(){
				while(running){
					//managing
				}
			}
		};
	}
	
	private void receive(){
		System.out.println("Running");
		receive = new Thread("Receive"){
			public void run(){
				System.out.println("Running");
				while(running){
					byte[] data = new byte[1024];
					DatagramPacket packet = new DatagramPacket(data, data.length);
					try {
						socket.receive(packet);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					process(packet);
					
				}
			}
		};
		receive.start();
	}
	
	private void sendToAll(String message){
		System.out.println("Sending to all");
		System.out.println("Clients :" + clients.size());
		for(ServerClient client:clients){
			send(message, client.address, client.port);
		}
	}
	
	private void send(String message, InetAddress address, int port){
		message += "/e/";
		send(message.getBytes(),address,port);
	}
	
	
	private void send(final byte[] data, final InetAddress address, final int port){
		send = new Thread("Send"){
			public void run(){
				DatagramPacket packet = new DatagramPacket(data,data.length, address, port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		};
		send.start();
	}
	
	private void process(DatagramPacket packet){
		String str = new String(packet.getData());
		if(str.startsWith("/c/")){
			int id = UniqueIdentifier.getIdentifier();
			clients.add(new ServerClient(str.split("/c/|/e/")[1],packet.getAddress(),packet.getPort(),id));
			System.out.println("Added");
			String message = "/c/" + id;
			send(message,packet.getAddress(),packet.getPort());
		}else if(str.startsWith("/m/")){
			String message = str;
			sendToAll(message);
		}
			System.out.println(str);
			
	}
}


