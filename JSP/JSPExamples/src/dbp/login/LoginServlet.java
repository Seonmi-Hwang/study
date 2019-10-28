package dbp.login;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Servlet implementation class HelloWorldServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	
    public void init() {			// life-cycle init method
 	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // doGet or doPost
		String id = request.getParameter("id");
		String pw = request.getParameter("password");
		System.out.println(id + ", " + pw);
		
		LoginService dao = new LoginService();
		User userInfo = dao.login(id, pw);	// 일종의 dto를 받아옴
		request.setAttribute("userInfo", userInfo);			
		RequestDispatcher rd = request.getRequestDispatcher("loginResult.jsp");
		rd.forward(request, response);
	}
		
	
}
