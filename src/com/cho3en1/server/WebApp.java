package com.cho3en1.server;

import java.util.Map;

public class WebApp {
	private static ServletContext contxt;
	static {
		contxt = new ServletContext();
		
		Map<String, String> mapping = contxt.getMapping();
		mapping.put("/login", "login");
		
		
		Map<String, Servlet> servlet = contxt.getServlet();
		servlet.put("login", new LoginServlet());
	}
	
	public static Servlet getServlet(String url) {
		if(null==url||(url=url.trim()).equals("")) {
			return null;
		}
		return contxt.getServlet().get(contxt.getMapping().get(url));
	}
}
