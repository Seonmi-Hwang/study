package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
	// request�� ó���� �� �̵��� URL�� ��ȯ
    public String execute(HttpServletRequest request, HttpServletResponse response) 
    		throws Exception;
}
