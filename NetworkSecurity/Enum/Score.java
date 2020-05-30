package practice19;

public enum Score { // user 기준
	WIN, LOSE, EQUAL;
	
	// add whatever methods you need
	public static String print(Score score) {
		switch(score) {
		case WIN:
			return "당신이 이겼습니다.";
		case LOSE:
			return "컴퓨터가 이겼습니다.";
		case EQUAL:
			return "비겼습니다.";
		default:
			return null;
		}
	}
}
