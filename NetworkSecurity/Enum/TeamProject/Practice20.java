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
      
      System.out.print("원하는 언어를 선택하세요 (1-한국어/2-영어/3-일본어): ");
      int ln = s.nextInt();
      
      System.out.println("컴퓨터의 생성 : " + com.decode(ln));
      System.out.print("당신의 입력 : ");
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