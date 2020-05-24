package practice16;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Practice16A {

	public static void main(String[] args) throws ClassNotFoundException, 
		NoSuchMethodException, SecurityException, InstantiationException, 
		IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Class<?> cls = Class.forName(args[0]);
		
		// find the method we want
		Method method = cls.getMethod(args[1]);		
		
		// create an instance
		Object methodObj = getInstance(cls);

		// invoke the method
		Object retobj = method.invoke(methodObj);

		System.out.println(retobj);
	}
	
	public static Object getInstance(Class<?> cinfo) throws NoSuchMethodException, 
			SecurityException, InstantiationException, IllegalAccessException, 
			IllegalArgumentException, InvocationTargetException {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		// formal parameters -- due to constructor overloading
		Class<?> params[] = new Class[3];
		params[0] = Integer.class;
		params[1] = Integer.class;
		params[2] = Integer.class;
		
		Constructor<?> ct = cinfo.getConstructor(params);
		
		System.out.print("날짜를 입력하세요 : ");
		String userInput = scan.next();
		String[] numbers = userInput.split("-");
		
		// actual parameters
		Object arglist[] = new Object[3];
		arglist[0] = new Integer(numbers[0]);
		arglist[1] = new Integer(numbers[1]);
		arglist[2] = new Integer(numbers[2]);
		
		return (ct.newInstance(arglist));
	}

}
