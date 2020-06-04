public enum Game {
	ROCK(0), PAPER(1), SCISSORS(2), ERROR(-1);
	
	int GameNum;
	Game(int num) {
		GameNum = num;
	}
	
	public int getGameNum() {
		return GameNum;
	}
}
