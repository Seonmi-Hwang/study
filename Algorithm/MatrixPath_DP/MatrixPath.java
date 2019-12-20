
public class MatrixPath {

	static int [][] m = {
		{6, 7, 12, 5},
		{5, 3, 11, 18},
		{7, 17, 3, 3},
		{8, 10, 14, 9}
	};
	
	static boolean [][] memo; // 계산이 되었는지 아닌지(T/F)를 저장하는 memo
	static int [][] dp; // memoization을 위한 memo
	
	public static int mat(int i, int j) { // (0, 0)에서 (i, j)까지의 최저점수를 구하는 함수
		if (memo[i][j]) return dp[i][j]; // 값이 존재하는 경우
		
		if (i == 0 && j == 0) { // (0, 0)일 때 초기화
			memo[i][j] = true;
			dp[i][j] = m[i][j];
		} else if (i == 0) { // 천장에 있을 때
			memo[i][j] = true;
			dp[i][j] = mat(0, j - 1) + m[i][j];
		} else if (j == 0) { // 벽에 있을 때
			memo[i][j] = true;
			dp[i][j] = mat(i - 1, 0) + m[i][j];
		} else { // 일반적인 경우
			memo[i][j] = true;
			dp[i][j] = Math.min(Math.min(mat(i - 1, j), mat(i, j - 1)), mat(i - 1, j - 1)) + m[i][j]; // 아래, 오른쪽, 오른쪽 아래 대각선
			// dp[i][j] = Math.min(mat(i - 1, j), mat(i, j - 1)) + m[i][j]; // 아래, 오른쪽만 성립
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
