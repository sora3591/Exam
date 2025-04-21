package bean;

import java.util.Map;

public class TestListSubject {
	private int entYear;
	private String stdentNo;
	private String studentNum;
	private String classNum;
	private Map<Integer, Integer> points;
	public int getEntYear() {
		return entYear;
	}
	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}
	public String getStdentNo() {
		return stdentNo;
	}
	public void setStdentNo(String stdentNo) {
		this.stdentNo = stdentNo;
	}
	public String getStudentNum() {
		return studentNum;
	}
	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}
	public String getClassNum() {
		return classNum;
	}
	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}
	public Map<Integer, Integer> getPoints() {
		return points;
	}
	public void setPoints(Map<Integer, Integer> points) {
		this.points = points;
	}

}
