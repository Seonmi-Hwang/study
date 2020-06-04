import java.util.Scanner;

//한국어 출력
public class Korean implements Language {

	public Korean(Game com) {}

	@Override
	public Game encode() {
		Scanner s = new Scanner(System.in);
		
		System.out.print("당신의 입력 : ");
		String input = s.next();
		s.close();
		
		if (input.equals("가위")) {
			return Game.SCISSORS;
		} else if (input.equals("바위")) {
			return Game.ROCK;
		} else if (input.equals("보")){
			return Game.PAPER;
		} else {
			return Game.ERROR;
		}
	}

	@Override
	public String decode(Game com) {
		String intro = "컴퓨터의 생성 : ";
		
		if (com.getGameNum() == 2) {
			return intro + "가위";
		} else if (com.getGameNum() == 0) {
			return intro + "바위";
		} else {
			return intro + "보";
		}
	}
	
	@Override
	public String print(Score s) {
		if (s == Score.WIN) {
			return "당신이 이겼습니다.";
		} else if (s == Score.LOSE) {
			return "컴퓨터가 이겼습니다.";
		} else {
			return "비겼습니다.";
		}
	}

}
