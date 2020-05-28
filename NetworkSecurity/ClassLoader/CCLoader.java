

import java.util.Scanner;

public class CCLoader extends ClassLoader {
	
	public CCLoader (ClassLoader classLoader) {
		super(classLoader);
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		// 로그인 기능 추가
		System.out.print("사용자 이름을 입력하세요 : ");
		String username = scan.next();
		System.out.print("패스워드를 입력하세요 : ");
		String password = scan.next();
		
		if (username.equals("dongduk") && password.equals("1234")) {
			System.out.println("Loading Class \"" + name + "\"");
			return super.loadClass(name);
		}
		
		return null;
	}
	
}
