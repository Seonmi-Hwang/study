

public enum Game {
	ROCK(0), PAPER(1), SCISSORS(2), ERROR(-1);
	
	int CodeNum;
	// Constructor
	Game (int num) {
		CodeNum = num;
	}
	
	// add whatever methods you need
	public int getCodeNum() {
		return CodeNum;
	}
	
	public static Game encode(String input) {
		switch(input) {
			case "����":
				return SCISSORS;
			case "����":
				return ROCK;
			case "��":
				return PAPER;
			default :
				return ERROR;	
		}
	}
	
	public String decode(int lang) {
		if (lang == 1) {
			switch(name()) {
				case "SCISSORS":
					return "����";
				case "ROCK":
					return "����";
				case "PAPER":
					return "��";
				default:
					return "����";
			}
		} // .. ����  �Ϻ��� �� �� ���
		return null;
	}
}
