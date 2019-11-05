package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import model.service.UserManager;
import model.User;

public class UpdateUserFormController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

	@Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String updateId = request.getParameter("userId");
		
		log.debug("UpdateForm Request : {}", updateId);

		UserManager manager = UserManager.getInstance();
		User user = manager.findUser(updateId);	// 사용자 정보 검색
		request.setAttribute("user", user);						
		
		HttpSession session = request.getSession();
		if (UserSessionUtils.isLoginUser(updateId, session) ||
			UserSessionUtils.isLoginUser("admin", session)) {
			// 현재 로그인한 사용자가 수정 대상 사용자이거나 관리자인 경우 -> 수정 가능
			
			return "/user/updateForm.jsp";   // 검색한 사용자 정보를 update form으로 전송     
		}
		
		// else (수정 불가능한 경우) 사용자 보기 화면으로 오류 메세지를 전달
		request.setAttribute("updateFailed", true);
		request.setAttribute("exception", 
			new IllegalStateException("타인의 정보는 수정할 수 없습니다."));            
		return "/user/view.jsp";	// 사용자 보기 화면으로 이동 (forwarding)
    }
}
