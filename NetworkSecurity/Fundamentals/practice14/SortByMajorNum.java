// 20170995 ��ǻ���а� 4�г� Ȳ����

package practice14;

import java.util.Comparator;

public class SortByMajorNum implements Comparator<Student> {

	@Override
	public int compare(Student o1, Student o2) {
		String m1 = o1.getMajor();
		String m2 = o2.getMajor();
		
		if (m1.equals(m2)) {
			int s1 = o1.getSid();
			int s2 = o2.getSid();
			
			// ��������
			if (s1 < s2) return -1;
			else if (s1 > s2) return 1;
			else return 0; // s1 == s2
		} else {
			return m1.compareTo(m2);
		}
	}
	
}
