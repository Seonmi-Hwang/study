import java.util.Scanner;

//�ѱ��� ���
public class Korean implements Language {

	public Korean(Game com) {}

	@Override
	public Game encode() {
		Scanner s = new Scanner(System.in);
		
		System.out.print("����� �Է� : ");
		String input = s.next();
		s.close();
		
		if (input.equals("����")) {
			return Game.SCISSORS;
		} else if (input.equals("����")) {
			return Game.ROCK;
		} else if (input.equals("��")){
			return Game.PAPER;
		} else {
			return Game.ERROR;
		}
	}

	@Override
	public String decode(Game com) {
		String intro = "��ǻ���� ���� : ";
		
		if (com.getGameNum() == 2) {
			return intro + "����";
		} else if (com.getGameNum() == 0) {
			return intro + "����";
		} else {
			return intro + "��";
		}
	}
	
	@Override
	public String print(Score s) {
		if (s == Score.WIN) {
			return "����� �̰���ϴ�.";
		} else if (s == Score.LOSE) {
			return "��ǻ�Ͱ� �̰���ϴ�.";
		} else {
			return "�����ϴ�.";
		}
	}

}
