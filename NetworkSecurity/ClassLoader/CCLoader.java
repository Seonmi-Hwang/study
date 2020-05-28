

import java.util.Scanner;

public class CCLoader extends ClassLoader {
	
	public CCLoader (ClassLoader classLoader) {
		super(classLoader);
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		// �α��� ��� �߰�
		System.out.print("����� �̸��� �Է��ϼ��� : ");
		String username = scan.next();
		System.out.print("�н����带 �Է��ϼ��� : ");
		String password = scan.next();
		
		if (username.equals("dongduk") && password.equals("1234")) {
			System.out.println("Loading Class \"" + name + "\"");
			return super.loadClass(name);
		}
		
		return null;
	}
	
}
