package practice19;

public enum Score { // user ����
	WIN, LOSE, EQUAL;
	
	// add whatever methods you need
	public static String print(Score score) {
		switch(score) {
		case WIN:
			return "����� �̰���ϴ�.";
		case LOSE:
			return "��ǻ�Ͱ� �̰���ϴ�.";
		case EQUAL:
			return "�����ϴ�.";
		default:
			return null;
		}
	}
}
