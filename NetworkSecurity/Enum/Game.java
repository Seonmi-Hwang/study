package practice19;

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
			case "가위":
				return SCISSORS;
			case "바위":
				return ROCK;
			case "보":
				return PAPER;
			default :
				return null;	
		}
	}
	
	public String decode() {
		switch(name()) {
			case "SCISSORS":
				return "가위";
			case "ROCK":
				return "바위";
			case "PAPER":
				return "보";
			default:
				return null;
		}
	}
}
