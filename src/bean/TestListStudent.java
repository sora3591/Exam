package bean;

public class TestListStudent {
    // 既存のフィールド
    private String subjectName;
    private String subjectCd;
    private int num; // Test number
    private int point;

    // フィールドを追加
    private int entYear;
    private String classNum;
    private String studentNo;
    private String studentName;

    // 2つの試験の点数を格納するフィールド
    private Integer point1;
    private Integer point2;

    // Getters and Setters for existing fields...
    public String getSubjectName() {
        return subjectName;
    }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    public String getSubjectCd() {
        return subjectCd;
    }
    public void setSubjectCd(String subjectCd) {
        this.subjectCd = subjectCd;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public int getPoint() {
        return point;
    }
    public void setPoint(int point) {
        // 例えば、DBからnullが返ってきた場合は-1や特定の値に設定する
        this.point = point;
    }

    // GetterとSetterを追加
    public int getEntYear() {
        return entYear;
    }
    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }
    public String getClassNum() {
        return classNum;
    }
    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }
    public String getStudentNo() {
        return studentNo;
    }
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    // GetterとSetterを追加
    public Integer getPoint1() {
        return point1;
    }
    public void setPoint1(Integer point1) {
        this.point1 = point1;
    }
    public Integer getPoint2() {
        return point2;
    }
    public void setPoint2(Integer point2) {
        this.point2 = point2;
    }
}
