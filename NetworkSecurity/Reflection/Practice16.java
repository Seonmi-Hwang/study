package practice16;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

public class Practice16 {

	public static void main(String[] args) throws ClassNotFoundException {
		Scanner scan = new Scanner(System.in);
		
		Class<?> cls = Class.forName(scan.next());
		Method mlist[] = cls.getDeclaredMethods();
		
		
		System.out.println("* Ŭ���� �̸� \n" + mlist[0].getDeclaringClass());
		
		System.out.println("* �Ҽ� ���� ���");
		Field fieldlist[] = cls.getDeclaredFields();
		int idx = 1;
		for (Field fld : fieldlist) {
			System.out.println("- �ҼӺ��� #" + idx);
			System.out.println("�̸� : " + fld.getName());
			System.out.println("Ÿ�� : " + fld.getType());
			int mod = fld.getModifiers();
			System.out.println("���� ������ : " + Modifier.toString(mod));
			idx++;
		}
		
		System.out.println("* �޼ҵ� �ñ״�ó");
		for (Method m : mlist) {
			System.out.println(m.toString());
		}
		
		System.out.println("* ������ �ñ״�ó ");
		Constructor<?> ctorlist[] = cls.getDeclaredConstructors();
		for (Constructor<?> ct : ctorlist) {
			System.out.println(ct.toString());
		}
	}
}
