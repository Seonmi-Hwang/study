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
		
		
		System.out.println("* 클래스 이름 \n" + mlist[0].getDeclaringClass());
		
		System.out.println("* 소속 변수 목록");
		Field fieldlist[] = cls.getDeclaredFields();
		int idx = 1;
		for (Field fld : fieldlist) {
			System.out.println("- 소속변수 #" + idx);
			System.out.println("이름 : " + fld.getName());
			System.out.println("타입 : " + fld.getType());
			int mod = fld.getModifiers();
			System.out.println("접근 제어자 : " + Modifier.toString(mod));
			idx++;
		}
		
		System.out.println("* 메소드 시그니처");
		for (Method m : mlist) {
			System.out.println(m.toString());
		}
		
		System.out.println("* 생성자 시그니처 ");
		Constructor<?> ctorlist[] = cls.getDeclaredConstructors();
		for (Constructor<?> ct : ctorlist) {
			System.out.println(ct.toString());
		}
	}
}
