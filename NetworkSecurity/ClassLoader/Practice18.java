
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Practice18 {

	public static void main(String[] args) throws ClassNotFoundException, 
			NoSuchMethodException, SecurityException, IllegalAccessException, 
			IllegalArgumentException, InvocationTargetException {
		
		// make a customized class loader 
		ClassLoader parent = Practice18.class.getClassLoader();
		CCLoader ccl = new CCLoader(parent);
		
		// load the target class
		Class<?> targetClass = ccl.loadClass(args[0]);
		
		if (targetClass == null) {
			System.out.println("아이디와 비밀번호가 올바르지 않습니다.");
			return;
		}
		
		// formal parameter 
		Class<?> param = (new String[0]).getClass();
		Class<?> mainArgType[] = {param};
		
		// get the main method
		Method main = targetClass.getMethod("main", mainArgType);
		
		// build an actual parameter
		String[] actualArg = new String[0];
		Object[] argsArray = {actualArg};
		
		// invoke the main method - static method
		main.invoke(null, argsArray);
	}

}
