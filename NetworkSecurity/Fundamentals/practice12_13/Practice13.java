// 20170995 ÄÄÇ»ÅÍÇÐ°ú 4ÇÐ³â È²¼±¹Ì

package practice12_13;

public class Practice13 {
	public static void main(String[] args) {
		MyInterface hello = new MyInterface() {
			public void calculate(int x, int y) {
				System.out.println("µ¡¼À : " + (x + y));
				System.out.println("»¬¼À : " + (x - y));
				System.out.println("°ö¼À : " + (x * y));
				System.out.println("³ª´°¼À : " + (x / y));
			}
		}; // Å¬·¡½º Á¤ÀÇ + °´Ã¼»ý¼º
		
		hello.calculate(10, 20);
	}
}
