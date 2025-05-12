package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

/**
 * テスト情報のデータアクセスオブジェクト
 */
public class TestDao extends Dao {

    /**
     * 単一のテスト情報を取得する
     */
    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Test test = null;
        String sql = "SELECT student_no, subject_cd, school_cd, no, point, class_num FROM test " +
                     "WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?";

        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
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

    /**
     * ResultSet からテストリストに変換
     */
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

    /**
     * 条件に合致するテストの一覧を取得する
     */
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<Test> list = new ArrayList<>();

        String sql = "SELECT student_no, subject_cd, school_cd, no, point, class_num FROM test " +
                     "WHERE school_cd = ? AND class_num = ? AND subject_cd = ? AND no = ? ORDER BY student_no ASC";

        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, school.getCd());
            statement.setString(2, classNum);
            statement.setString(3, subject.getCd());
            statement.setInt(4, num);

            try (ResultSet rSet = statement.executeQuery()) {
                list = postFilter(rSet, school);
            }
        }

        return list;
    }

    /**
     * テストリストを一括保存（トランザクション管理）
     */
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

    /**
     * 単一のテストを保存（INSERTまたはUPDATE）
     */
    public boolean save(Test test, Connection connection) throws Exception {
        int count = 0;
        ResultSet rSet = null;

        if (test.getStudent() == null || test.getSubject() == null || test.getSchool() == null) {
            throw new IllegalArgumentException("Test object is missing required associations (student, subject, or school).");
        }


        try (
            PreparedStatement checkStmt = connection.prepareStatement(
                "SELECT * FROM test WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?"
            )
        ) {
            checkStmt.setString(1, test.getStudent().getNo());
            checkStmt.setString(2, test.getSubject().getCd());
            checkStmt.setString(3, test.getSchool().getCd());
            checkStmt.setInt(4, test.getNo());

            rSet = checkStmt.executeQuery();

            if (rSet.next()) {
                // UPDATE
                try (PreparedStatement updateStmt = connection.prepareStatement(
                        "UPDATE test SET point = ?, class_num = ? WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?"
                )) {
                    updateStmt.setInt(1, test.getPoint());
                    updateStmt.setString(2, test.getClassNum());
                    updateStmt.setString(3, test.getStudent().getNo());
                    updateStmt.setString(4, test.getSubject().getCd());
                    updateStmt.setString(5, test.getSchool().getCd());
                    updateStmt.setInt(6, test.getNo());

                    count = updateStmt.executeUpdate();
                }
            } else {
                // INSERT
                try (PreparedStatement insertStmt = connection.prepareStatement(
                        "INSERT INTO test (student_no, subject_cd, school_cd, no, point, class_num) VALUES (?, ?, ?, ?, ?, ?)"
                )) {
                    insertStmt.setString(1, test.getStudent().getNo());
                    insertStmt.setString(2, test.getSubject().getCd());
                    insertStmt.setString(3, test.getSchool().getCd());
                    insertStmt.setInt(4, test.getNo());
                    insertStmt.setInt(5, test.getPoint());
                    insertStmt.setString(6, test.getClassNum());

                    count = insertStmt.executeUpdate();
                }
            }
        } finally {
            if (rSet != null) rSet.close();
        }

        return count > 0;
    }
}
