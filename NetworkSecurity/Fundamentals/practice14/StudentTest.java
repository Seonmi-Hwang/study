// 20170995 컴퓨터학과 4학년 황선미

package practice14;

import java.util.ArrayList;
import java.util.Collections;

public class StudentTest {

	public static void main(String[] args) {
		ArrayList<Student> sarray = new ArrayList<Student>();
		
		sarray.add(new Student("computer", 20191234, "kim"));
		sarray.add(new Student("computer", 20153456, "yi"));
		sarray.add(new Student("business", 20192468, "lee"));
		sarray.add(new Student("music", 20162468, "park"));
		sarray.add(new Student("business", 20143456, "han"));
		
		System.out.println(sarray);
		
		Collections.sort(sarray, new SortByNumDesc());
		System.out.println(sarray);
		
		Collections.sort(sarray, new SortByMajorNum());
		System.out.println(sarray);
	}

}
