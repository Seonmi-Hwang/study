
public class PathFinding_final {

	/*
	static int[][]map = {
			{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 2},
			{1, 1, 1, 1, 1, 1, 2, 0},
	};
	*/
	
	static int [][]map = {
			{2, 5, 1, 6, 1, 3, 1},
			{6, 7, 1, 2, 4, 3, 7},
			{7, 2, 3, 2, 1, 3, 2},
			{2, 5, 3, 1, 8, 1, 4},
			{3, 1, 2, 3, 4, 2, 2},
			{2, 2, 3, 4, 1, 2, 1},
			{1, 9, 5, 2, 8, 7, 0}
	};
	
	
	public static boolean find(int x, int y) {
		if (x >= map.length) return false;
		if (y >= map.length) return false;
		if (x == map.length - 1 && y == map.length) return true;
		
		if (find(x + map[x][y], y))
			return true;
			
		if (find(x, y + map[x][y]))
			return true;
		
		return false;
	}
	
	static int [][]memo;
	static int [][]boolMemo;
	/* 재귀 호출. 중복 호출을 방지*/
	public static boolean find_memo(int x, int y) {
		if (x >= map.length) return false;
		if (y >= map.length) return false;
		if (x == map.length-1 && y == map.length) return true;
		
		if (memo[x][y] != -1) return memo[x][y] == 1; // 계산이 되어 있다. (0,0)~(x,y)까지 path가 있으면 1로 표시됨
		
		memo[x][y] = 0;	// 계산이 안 되어 있다.
		if (find_memo(x + map[x][y], y))
			memo[x][y] = 1;
		if (find_memo(x, y + map[x][y]))
			memo[x][y] = 1;
		
		return memo[x][y] == 1;
	}
	
	static int [][]dp;
	/* Dynamic Programming. memo의 기능만 생각 */
	public static boolean find_DP(int x, int y) {
		// 현재 지점이 1이면 (0, 0)~(i, j)까지 path가 있으므로 더 가보자
		dp = new int[map.length][map.length];
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map.length; j++)
				dp[i][j] = 0;
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (dp[i][j] == 1) {
					if (i + map[i][j] < map.length)
						dp[i + map[i][j]][j] = 1;
					if (j + map[i][j] < map.length)
						dp[i][j + map[i][j]] = 1;
				}
			}
		}
		
		return dp[map.length-1][map.length-1] == 1;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		memo = new int[map.length][map.length];
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j  <map.length; j++)
				memo[i][j] = -1;
		
		//System.out.println(find(0,0));
		System.out.println(find_memo(0, 2));
		System.out.println(find_DP(0, 2));
	}

}
