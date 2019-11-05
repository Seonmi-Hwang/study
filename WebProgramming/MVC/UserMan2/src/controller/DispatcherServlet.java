package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@WebServlet(name = "userSevlet", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    
    private RequestMapping rm;

    @Override
    public void init() throws ServletException {
        rm = new RequestMapping();
        rm.initMapping();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) 
    	throws ServletException, IOException {
    	logger.debug("Method : {}, Request URI : {}, ServletPath : {}", 
    			request.getMethod(), request.getRequestURI(), request.getServletPath());
    	String contextPath = request.getContextPath();
    	String servletPath = request.getServletPath();
    	
    	// URL 중 servletPath에 대응되는 controller를 구함
        Controller controller = rm.findController(servletPath);
        try {
        	// controller를 통해 request 처리 후, 이동할 uri를 반환 받음
            String uri = controller.execute(request, response);
            
 			// 반환된 uri에 따라 forwarding 또는 redirection 여부를 결정하고 이동 
            if (uri.startsWith("redirect:")) {	
            	// redirection 지시
            	String targetUri = contextPath + uri.substring("redirect:".length());
            	response.sendRedirect(targetUri);	// redirect to url            
            }
            else {
            	// forwarding 수행
            	RequestDispatcher rd = request.getRequestDispatcher(uri);
                rd.forward(request, response);		// forward to the view page
            }                   
        } catch (Exception e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }
}
