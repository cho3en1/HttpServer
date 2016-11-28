package com.cho3en1.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

import com.cho3en1.util.CloseUtil;

public class Response {

	public static final String CRLF = "\r\n";
	public static final String BLANK = " ";
	
	private StringBuilder content;
	private StringBuilder headInfo;
	private int len;
	
	private BufferedWriter bw;
	
	public Response() {
		headInfo = new StringBuilder();
		content = new StringBuilder();
		len = 0;
	}
	
	public Response(Socket client) {
		this();
		try {
			bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			headInfo = null;
		}
	}
	
	public Response(OutputStream os) {
		this();
		bw = new BufferedWriter(new OutputStreamWriter(os));
	}
	
	public Response print(String info) {
		content.append(info);
		len += info.getBytes().length;
		return this;
	}
	
	public Response println(String info) {
		content.append(info);
		len += (info+CRLF).getBytes().length;
		return this;
	}
	
	private void creatHeadInfo(int code) {
		headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
		switch(code) {
			case 200:
				headInfo.append("OK");
			case 404:
				headInfo.append("NOT FOUND");
			case 505:
				headInfo.append("SERVER ERROR");
		}
		headInfo.append(CRLF);
		headInfo.append("Server:Apache").append(BLANK).append("Tomcat/6.0.12").append(CRLF);
		headInfo.append("Date:").append(new Date()).append(CRLF);
		headInfo.append("Content-type:text/html;charset=GBK").append(CRLF);
		headInfo.append("Content-Length:").append(len).append(CRLF);
		headInfo.append(CRLF);
	}
	
	void pushToClient(int code) throws IOException {
		if(null==headInfo) {
			code = 500;
		}
		creatHeadInfo(code);
		bw.append(headInfo.toString());
		bw.append(content.toString());
		bw.flush();
	}
	
	public void close() throws IOException {
		CloseUtil.closeAll(bw);
	}
}
