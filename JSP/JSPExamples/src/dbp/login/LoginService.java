package dbp.login;

public class LoginService {
	public User login(String id, String pw) {
		System.out.println(id + ", " + pw);
		
		// �α��� ó�� �� ����� ���� �˻� ���� ...
		
		User user = new User("Jain", 22, "010-3333-4444"); // �α��ε� ����� ����
		return user; // ������ dto��� ���� ��. loginServlet���� �ٽ� ���!
	}
}
