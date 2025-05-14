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
                testListStudent.setStudentNo(rSet.getString("student_no"));
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
        String sqlSelect = "SELECT s.ent_year, s.class_num, s.student_no, s.name";
        String sqlFrom = " FROM student s";
        String sqlWhere = " WHERE s.school_cd = ?";
        String sqlGroupBy = "";
        String sqlOrderBy = " ORDER BY s.ent_year, s.class_num, s.student_no";

        List<Object> params = new ArrayList<>();
        params.add(school.getCd());

        if (entYear != 0) {
            sqlWhere += " AND s.ent_year = ?";
            params.add(entYear);
        }
        if (classNum != null && !classNum.equals("0")) {
            sqlWhere += " AND s.class_num = ?";
            params.add(classNum);
        }
        if (studentNo != null && !studentNo.isEmpty()) {
            sqlWhere += " AND s.student_no = ?";
            params.add(studentNo);
        }

        // 学科ごとの試験点数を取得する部分
        if (subjectCd != null && !subjectCd.equals("0")) {
            sqlSelect += ", MAX(CASE WHEN t.num = 1 THEN t.point END) AS point1, MAX(CASE WHEN t.num = 2 THEN t.point END) AS point2";
            sqlFrom += " LEFT JOIN test t ON s.student_no = t.student_no AND s.school_cd = t.school_cd";
            sqlWhere += " AND t.subject_cd = ?"; // 選択された科目の試験をフィルタリング
            params.add(subjectCd);
            sqlGroupBy = " GROUP BY s.ent_year, s.class_num, s.student_no, s.name";
        } else {
            // 特定の科目が選択されていない場合は、点数のカラムにNULLを設定する
            sqlSelect += ", CAST(NULL AS INTEGER) AS point1, CAST(NULL AS INTEGER) AS point2";
        }

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
