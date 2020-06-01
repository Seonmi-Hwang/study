import java.util.Random;
import java.util.Scanner;

public class Practice20 {
   
   static Score[][] scoreBoard = {
         {Score.EQUAL, Score.LOSE, Score.WIN},
         {Score.WIN, Score.EQUAL, Score.LOSE},
         {Score.LOSE, Score.WIN, Score.EQUAL}
   };
   
   public static void main(String[] args) {
      Scanner s = new Scanner(System.in);
      
      Random r = new Random();
      int num = r.nextInt(3);
      
      Game com;
      if (num == 0) {
         com = Game.ROCK;
      } else if (num == 1) {
         com = Game.SCISSORS;
      } else {
         com = Game.PAPER;
      }
      
      System.out.print("���ϴ� �� �����ϼ��� (1-�ѱ���/2-����/3-�Ϻ���): ");
      int ln = s.nextInt();
      
      System.out.println("��ǻ���� ���� : " + com.decode(ln));
      System.out.print("����� �Է� : ");
      String yourInput = s.next();
         
      Game user = Game.encode(yourInput);
         
      Score rslt = whoswin(user, com);
         
      System.out.println(Score.print(rslt));
         
      s.close();
         
   }
   
   public static Score whoswin(Game user, Game com) {
      return scoreBoard[user.ordinal()][com.ordinal()];
   }
}