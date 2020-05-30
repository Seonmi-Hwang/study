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
		System.out.println("��ǻ���� ���� : " + com.decode());
		
		System.out.print("����� �Է� : ");
		String yourInput = s.next();
		Game user = Game.encode(yourInput);
		
		try {
			Score rslt = whoswin(user, com);
			System.out.println(Score.print(rslt));
		} catch (NullPointerException e) {
			System.out.println("�Է��� �ùٸ��� �ʽ��ϴ�.");
		} finally {
			s.close();
		}
	}
	
	public static Score whoswin(Game user, Game com) {
		return scoreBoard[user.CodeNum][com.CodeNum];
	}

}
