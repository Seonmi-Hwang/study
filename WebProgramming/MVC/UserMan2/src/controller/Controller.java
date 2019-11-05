package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
	// request를 처리한 후 이동할 URL을 반환
    public String execute(HttpServletRequest request, HttpServletResponse response) 
    		throws Exception;
}
