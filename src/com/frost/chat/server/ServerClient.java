package com.frost.chat.server;

import java.net.InetAddress;

public class ServerClient {
	public String name;
	public InetAddress address;
	public int port;
	public final int ID;
	public int attempt;
	
	public ServerClient(String name, InetAddress address, int port, final int ID){
		this.name = name;
		this.address = address;
		this.port = port;
		this.ID = ID;
	}
	
	public int getID(){
		return ID;
	}
	
}
