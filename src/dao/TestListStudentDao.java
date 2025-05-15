package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

    private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
        List<TestListStudent> list = new ArrayList<>();
        try {
            while (rSet.next()) {
                TestListStudent testListStudent = new TestListStudent();
                testListStudent.setEntYear(rSet.getInt("ent_year"));
                testListStudent.setClassNum(rSet.getString("class_num"));
                // STUDENTテーブルの実際のカラム名 'no' を使用
                testListStudent.setStudentNo(rSet.getString("no"));
                testListStudent.setStudentName(rSet.getString("name"));

                // point1とpoint2を取得し、nullの場合を考慮する
                Object p1Obj = rSet.getObject("point1");
                if (p1Obj != null) {
                    testListStudent.setPoint1(((Number)p1Obj).intValue());
                } else {
                    testListStudent.setPoint1(null);
                }

                Object p2Obj = rSet.getObject("point2");
                if (p2Obj != null) {
                    testListStudent.setPoint2(((Number)p2Obj).intValue());
                } else {
                    testListStudent.setPoint2(null);
                }
                list.add(testListStudent);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            throw new Exception("Error processing result set: " + e.getMessage());
        }
        return list;
    }

    public List<TestListStudent> filter(int entYear, String classNum, String subjectCd, String studentNo, School school) throws Exception {
        List<TestListStudent> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        // 学生情報の基本SELECT文
        // s.student_no を s.no に変更
        String sqlSelect = "SELECT s.ent_year, s.class_num, s.no, s.name";
        String sqlFrom = " FROM student s";
        String sqlWhere = ""; // Initialize sqlWhere
        String sqlGroupBy = "";
        String sqlOrderBy = " ORDER BY s.ent_year, s.class_num, s.no";

        List<Object> params = new ArrayList<>();

        // Handle subject selection first, as its '?' appears in sqlFrom
        if (subjectCd != null && !subjectCd.equals("0")) {
            // T.NUM を T.NO に修正
            sqlSelect += ", MAX(CASE WHEN t.no = 1 THEN t.point END) AS point1, MAX(CASE WHEN t.no = 2 THEN t.point END) AS point2";
            sqlFrom += " LEFT JOIN test t ON s.no = t.student_no AND s.school_cd = t.school_cd AND t.subject_cd = ?";
            params.add(subjectCd); // SubjectCd パラメータを最初に追加
            sqlGroupBy = " GROUP BY s.ent_year, s.class_num, s.no, s.name";
        } else {
            sqlSelect += ", CAST(NULL AS INTEGER) AS point1, CAST(NULL AS INTEGER) AS point2";
        }

        // Build WHERE clause
        // School Code is always a condition
        sqlWhere = " WHERE s.school_cd = ?";
        params.add(school.getCd());

        if (entYear != 0) {
            sqlWhere += " AND s.ent_year = ?";
            params.add(entYear);
        }
        // classNum が空でなく、かつ "0" (すべて) でない場合に条件追加
        if (classNum != null && !classNum.isEmpty() && !classNum.equals("0")) {
            sqlWhere += " AND s.class_num = ?";
            params.add(classNum);
        }
        if (studentNo != null && !studentNo.isEmpty()) {
            sqlWhere += " AND s.no = ?";
            params.add(studentNo);
        }

        // Combine SQL parts
        String fullSql = sqlSelect + sqlFrom + sqlWhere + sqlGroupBy + sqlOrderBy;

        System.out.println("Executing SQL (TestListStudentDao): " + fullSql); // デバッグ用
        for (Object p : params) {
            System.out.println("Param: " + p);
        }

        try {
            statement = connection.prepareStatement(fullSql);
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }

            rSet = statement.executeQuery();
            list = postFilter(rSet);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (rSet != null) {
                try {
                    rSet.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
        return list;
    }
}
