package dbp.servlet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import java.text.*;
import java.io.*;

/**
 * Servlet implementation class HelloWorldServlet
 */
public class HelloWorldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloWorldServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	
    private String title; 		 	
  	private String time;
	public void init() {			// life-cycle init method
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = format.format(new Date());
 	}


	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		title = "Hello World!"; 
	    int start_num = 1, end_num = 5;
		response.setContentType("text/html; charset=EUC_KR");
	    PrintWriter out = response.getWriter();
		out.print("<HTML><HEAD><TITLE>"); out.print(title); out.println("</TITLE></HEAD></HTML>");
	    out.println("<BODY>");
	    out.println("<H1>HelloWorldSevlet</H1>");
	    out.println("<B>" + title + "</B><BR>");
	 	for(int i = start_num; i <= end_num; i++) {
			out.print(i); out.print(" : "); out.print(time); out.println("<br/>"); 
	    }   	
		out.println("</BODY>"); out.println("</HTML>");
	}
}
