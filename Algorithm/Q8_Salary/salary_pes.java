// Update 19.10.15 by Seonmi-Hwang
public class salary {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(getTotalSalary());
	}

	static String organization[] = {
			"NYYNN", 
			"NNNNN", 
			"NNNYY", 
			"NYNNN", 
			"NNNNN"
	};
	
	public static int getSalary(int emp_id) {
		int total = 0;
		char[] staff = organization[emp_id].toCharArray();
		
		for (int i = 0; i < staff.length; i++)	
			if (i != emp_id && staff[i] == 'Y')
				total += getSalary(i); // memoization으로 중복호출을 막아야 함!
		
		return total == 0 ? 1 : total;
		
	}
	
	public static int getTotalSalary() {
		int total = 0;
		
		for (int i = 0; i < organization.length; i++)
			total += getSalary(i);
		
		return total;	
	}
}
