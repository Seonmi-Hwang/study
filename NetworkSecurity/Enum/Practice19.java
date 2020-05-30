package practice19;

import java.util.Scanner;

public class Practice19 {

	final static Score[][] scoreBoard = {	// row for USER, column for COM
		{Score.EQUAL, Score.LOSE, Score.WIN},
		{Score.WIN, Score.EQUAL, Score.LOSE},
		{Score.LOSE, Score.WIN, Score.EQUAL}
	};
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		Game com = Game.SCISSORS;
		System.out.println("컴퓨터의 생성 : " + com.decode());
		
		System.out.print("당신의 입력 : ");
		String yourInput = s.next();
		Game user = Game.encode(yourInput);
		
		try {
			Score rslt = whoswin(user, com);
			System.out.println(Score.print(rslt));
		} catch (NullPointerException e) {
			System.out.println("입력이 올바르지 않습니다.");
		} finally {
			s.close();
		}
	}
	
	public static Score whoswin(Game user, Game com) {
		return scoreBoard[user.CodeNum][com.CodeNum];
	}

}
