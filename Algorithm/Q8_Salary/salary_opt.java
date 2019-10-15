// Update 19.10.15 by Seonmi-Hwang
public class salary_opt {

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
	
	public static int getSalary(int emp_id, int memo[]) {
		int total = 0;
		
		if (memo[emp_id] != 0) return memo[emp_id];
		
		char[] staff = organization[emp_id].toCharArray();
		
		for (int i = 0; i < staff.length; i++)	
			if (i != emp_id && staff[i] == 'Y')
				total += getSalary(i, memo); 
		
		memo[emp_id] = (total == 0 ? 1 : total);
		
		return memo[emp_id];
	}
	
	public static int getTotalSalary() {
		int total = 0;
		int memo[] = new int[organization.length];	// 사람 수 만큼 memo가 필요
		
		for (int i = 0;i < organization.length; i++)
			memo[i] = 0;	// 계산이 안되어있다는 뜻
		
		for (int i = 0; i < organization.length; i++)
			total += getSalary(i, memo);
		
		return total;
		
	}
}
