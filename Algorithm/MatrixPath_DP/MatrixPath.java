
public class MatrixPath {

	static int [][] m = {
		{6, 7, 12, 5},
		{5, 3, 11, 18},
		{7, 17, 3, 3},
		{8, 10, 14, 9}
	};
	
	static boolean [][] memo; // ����� �Ǿ����� �ƴ���(T/F)�� �����ϴ� memo
	static int [][] dp; // memoization�� ���� memo
	
	public static int mat(int i, int j) { // (0, 0)���� (i, j)������ ���������� ���ϴ� �Լ�
		if (memo[i][j]) return dp[i][j]; // ���� �����ϴ� ���
		
		if (i == 0 && j == 0) { // (0, 0)�� �� �ʱ�ȭ
			memo[i][j] = true;
			dp[i][j] = m[i][j];
		} else if (i == 0) { // õ�忡 ���� ��
			memo[i][j] = true;
			dp[i][j] = mat(0, j - 1) + m[i][j];
		} else if (j == 0) { // ���� ���� ��
			memo[i][j] = true;
			dp[i][j] = mat(i - 1, 0) + m[i][j];
		} else { // �Ϲ����� ���
			memo[i][j] = true;
			dp[i][j] = Math.min(Math.min(mat(i - 1, j), mat(i, j - 1)), mat(i - 1, j - 1)) + m[i][j]; // �Ʒ�, ������, ������ �Ʒ� �밢��
			// dp[i][j] = Math.min(mat(i - 1, j), mat(i, j - 1)) + m[i][j]; // �Ʒ�, �����ʸ� ����
		}
		return dp[i][j];
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		memo = new boolean[m.length][m.length];
		for (int i = 0; i < m.length; i++)
			for (int j = 0; j < m.length; j++)
				memo[i][j] = false;
		
		dp = new int[m.length][m.length];
		
		System.out.println(mat(2, 2));
	}
}
