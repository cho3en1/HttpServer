package com.cho3en1.server;

public class LoginServlet extends Servlet{

	@Override
	public void doGet(Request req, Response rep) throws Exception {
		rep.println("<html><head><title>��ӭ����</title>");
		rep.println("</head><body>");
		rep.println("��ӭ��").println(req.getParameter("uname")).println("����");
		rep.println("</body></html>");
		
	}

	@Override
	public void doPost(Request req, Response rep) throws Exception {
		
	}
	
}
