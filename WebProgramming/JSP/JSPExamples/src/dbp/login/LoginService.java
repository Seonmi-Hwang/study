package dbp.login;

public class LoginService {
	public User login(String id, String pw) {
		System.out.println(id + ", " + pw);
		
		// 로그인 처리 후 사용자 정보 검색 실행 ...
		
		User user = new User("Jain", 22, "010-3333-4444"); // 로그인된 사용자 정보
		return user; // 일종의 dto라고 보면 됨. loginServlet으로 다시 고고!
	}
}
