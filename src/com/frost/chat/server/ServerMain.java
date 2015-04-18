package com.frost.chat.server;

public class ServerMain {

	public ServerMain(int port){
		new Server(port);
	}
	public static void main(String args[]){
		if(args.length != 1){
			System.out.println("Usuage : java -jar ServerMain [port]");
			return;
		}
		int port = Integer.parseInt(args[0]);
		new ServerMain(port);
	}
	
	
	
}
