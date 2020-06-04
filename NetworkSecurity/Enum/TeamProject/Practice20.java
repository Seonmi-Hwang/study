import java.util.Random;
import java.util.Scanner;

public class Practice20 {

   static Score[][] scoreBoard = {
         {Score.EQUAL, Score.LOSE, Score.WIN},
         {Score.WIN, Score.EQUAL, Score.LOSE},
         {Score.LOSE, Score.WIN, Score.EQUAL}
   };
   
   public static Score whoswin(Game user, Game com) {
      try {
         return scoreBoard[user.getGameNum()][com.getGameNum()];
      } catch (ArrayIndexOutOfBoundsException e) {
           System.out.println("다시 입력하십시오.");
           return null;
       }
   }
   
   public static void main(String[] args) {
      
      Scanner s = new Scanner(System.in);
      
      /* 컴퓨터 값 생성 */
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
      
      /* 언어 선택 */
      System.out.print("원하는 언어를 선택하세요 (1-한국어/2-영어/3-일본어): ");
      int ln = s.nextInt();
   
      Language l = null;
      if (ln == 1) {
         l = new Korean(com);
      } else {
         l = new English(com);         
      }
      System.out.println(l.decode(com));
      Game user = l.encode();
            
      /* 게임 결과 출력 */
      Score score = whoswin(user, com);
      if (score != null) {
         String rslt = l.print(score);
         System.out.println(rslt);            
      }
      s.close();
   }
   
}
