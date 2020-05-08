// 20170995 컴퓨터학과 4학년 황선미

package practice14;

public class Student {
	private int sid;
	private String major, name;
	
	public Student(String major, int sid, String name) {
		super();
		this.major = major;
		this.sid = sid;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return major + "/" + sid + "/" + name;
	}

	public int getSid() {
		return sid;
	}

	public String getMajor() {
		return major;
	}

	public String getName() {
		return name;
	}
}
