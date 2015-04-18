package com.frost.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client  {


	String name, address;
	int port;
	private int ID = -1;

	private Thread send;

	private DatagramSocket socket;
	private InetAddress ip; //its an ip address
	
	public Client(String name, String address, int port) {
		this.name = name;
		this.address = address;
		this.port = port;
		
	}
	
	public void hello(){
		System.out.println("Hello world!");
	}
	
	public String getName(){
		return name;
	}
	
	public String getAddress(){
		return address;
	}
	
	
	public int port(){
		return port;
	}
	
	public int getID(){
		return ID;
	}
	
	public void setID(int ID){
		this.ID = ID ;
	}
	
	public boolean openConnection(String address, int port){
		try{
			socket = new DatagramSocket();
			ip = InetAddress.getByName(address);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public String receive(){
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data,data.length);
		try{
			socket.receive(packet);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		String message = new String(packet.getData());
		
		return message;
	}
	
	public void send(final byte[] data){
		send = new Thread("Send"){
			public void run(){
				DatagramPacket packet = new DatagramPacket(data,data.length, ip, port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		};
		send.start();
	}

}
