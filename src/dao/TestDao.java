package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

    private String baseSql = "SELECT * FROM test WHERE school_cd = ?";

    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Test test = null;
        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM test WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?"
            )
        ) {
            statement.setString(1, student.getNo());
            statement.setString(2, subject.getCd());
            statement.setString(3, school.getCd());
            statement.setInt(4, no);

            try (ResultSet rSet = statement.executeQuery()) {
                if (rSet.next()) {
                    test = new Test();
                    test.setStudent(student);
                    test.setSubject(subject);
                    test.setSchool(school);
                    test.setNo(rSet.getInt("no"));
                    test.setPoint(rSet.getInt("point"));
                    test.setClassNum(rSet.getString("class_num"));
                }
            }
        }
        return test;
    }

    public List<Test> postFilter(ResultSet rSet, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        StudentDao studentDao = new StudentDao();
        SubjectDao subjectDao = new SubjectDao();

        while (rSet.next()) {
            Test test = new Test();
            test.setNo(rSet.getInt("no"));
            test.setPoint(rSet.getInt("point"));
            test.setClassNum(rSet.getString("class_num"));

            Student student = studentDao.get(rSet.getString("student_no"));
            Subject subject = subjectDao.get(rSet.getString("subject_cd"), school);

            test.setStudent(student);
            test.setSubject(subject);
            test.setSchool(school);

            list.add(test);
        }

        return list;
    }

    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<Test> list = new ArrayList<>();

        String sql = baseSql +
            " AND class_num = ? AND subject_cd = ? AND no = ? ORDER BY student_no ASC";

        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);
            statement.setString(4, subject.getCd());
            statement.setInt(5, num);

            try (ResultSet rSet = statement.executeQuery()) {
                list = postFilter(rSet, school);
            }
        }

        return list;
    }

    public boolean save(List<Test> list) throws Exception {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (Test test : list) {
                    save(test, connection);
                }
                connection.commit();
                return true;
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public boolean save(Test test, Connection connection) throws Exception {
        ResultSet rSet = null;
        PreparedStatement checkStmt = null;
        PreparedStatement execStmt = null;
        int count = 0;

        try {
            String checkSql = "SELECT * FROM test WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?";
            checkStmt = connection.prepareStatement(checkSql);
            checkStmt.setString(1, test.getStudent().getNo());
            checkStmt.setString(2, test.getSubject().getCd());
            checkStmt.setString(3, test.getSchool().getCd());
            checkStmt.setInt(4, test.getNo());

            rSet = checkStmt.executeQuery();

            if (rSet.next()) {
                // UPDATE
                String updateSql = "UPDATE test SET point = ?, class_num = ? WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?";
                execStmt = connection.prepareStatement(updateSql);
                execStmt.setInt(1, test.getPoint());
                execStmt.setString(2, test.getClassNum());
                execStmt.setString(3, test.getStudent().getNo());
                execStmt.setString(4, test.getSubject().getCd());
                execStmt.setString(5, test.getSchool().getCd());
                execStmt.setInt(6, test.getNo());
            } else {
                // INSERT
                String insertSql = "INSERT INTO test (student_no, subject_cd, school_cd, no, point, class_num) VALUES (?, ?, ?, ?, ?, ?)";
                execStmt = connection.prepareStatement(insertSql);
                execStmt.setString(1, test.getStudent().getNo());
                execStmt.setString(2, test.getSubject().getCd());
                execStmt.setString(3, test.getSchool().getCd());
                execStmt.setInt(4, test.getNo());
                execStmt.setInt(5, test.getPoint());
                execStmt.setString(6, test.getClassNum());
            }

            count = execStmt.executeUpdate();
        } finally {
            if (rSet != null) try { rSet.close(); } catch (SQLException e) { throw e; }
            if (checkStmt != null) try { checkStmt.close(); } catch (SQLException e) { throw e; }
            if (execStmt != null) try { execStmt.close(); } catch (SQLException e) { throw e; }
        }

        return count > 0;
    }
}
