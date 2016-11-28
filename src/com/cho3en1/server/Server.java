package com.cho3en1.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import com.cho3en1.util.CloseUtil;

public class Server {
	
	public static final String CRLF = "\r\n";
	public static final String BLANK = " ";
	
	private ServerSocket server;
	
	private boolean isShutDown = false;
	
	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.start();
	}
	
	public void start() {
		start(8888);
	}
	
	public void start(int port) {
		try {
			server = new ServerSocket(port);
			this.receive();
		} catch (IOException e) {
			stop();
		}
	}
	
	private void receive(){
		try {
			while(!isShutDown) {
				new Thread(new Dispatcher(server.accept())).start();
			}
			
		} catch (IOException e) {
			stop();
		}
	}
	
	public void stop() {
		isShutDown = true;
		CloseUtil.closeAll(server);
	}
}
