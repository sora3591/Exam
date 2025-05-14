package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {
    private String baseSql = "SELECT s.no AS student_no, s.name AS student_name, s.ent_year, s.class_num, t.no AS test_no, t.point "
                           + "FROM student s JOIN test t ON s.no = t.student_no "
                           + "WHERE s.ent_year = ? AND s.class_num = ? AND t.subject_cd = ? AND s.school_cd = ? "
                           + "ORDER BY s.no";

    private List<TestListSubject> postFilter(ResultSet rSet, School school) throws Exception {
        Map<String, TestListSubject> studentMap = new HashMap<>();
        try {
            while (rSet.next()) {
                String studentNo = rSet.getString("student_no");
                TestListSubject testListSubject = studentMap.get(studentNo);

                if (testListSubject == null) {
                    testListSubject = new TestListSubject();
                    testListSubject.setEntYear(rSet.getInt("ent_year"));
                    testListSubject.setStudentNo(studentNo);
                    testListSubject.setStudentName(rSet.getString("student_name"));
                    testListSubject.setClassNum(rSet.getString("class_num"));
                    testListSubject.setPoints(new HashMap<>());
                    studentMap.put(studentNo, testListSubject);
                }

                int point = rSet.getInt("point");
                if (!rSet.wasNull()) {
                   testListSubject.getPoints().put(rSet.getInt("test_no"), point);
                }
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            throw e;
        }
        return new ArrayList<>(studentMap.values());
    }

    public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
        List<TestListSubject> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        try {
            statement = connection.prepareStatement(baseSql);
            statement.setInt(1, entYear);
            statement.setString(2, classNum);
            statement.setString(3, subject.getCd());
            statement.setString(4, school.getCd());
            rSet = statement.executeQuery();
            list = postFilter(rSet, school);
        } catch (Exception e) {
            throw e;
        } finally {
            if (rSet != null) { try { rSet.close(); } catch (SQLException sqle) { throw sqle; } }
            if (statement != null) { try { statement.close(); } catch (SQLException sqle) { throw sqle; } }
            if (connection != null) { try { connection.close(); } catch (SQLException sqle) { throw sqle; } }
        }
        return list;
    }
}
