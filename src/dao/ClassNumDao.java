package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao {

    public ClassNum get(String class_num, School school) throws Exception {
        ClassNum classNum = new ClassNum();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("select * from class_num where class_num=? and school_cd=?");
            statement.setString(1, class_num);
            statement.setString(2, school.getCd());
            ResultSet rSet = statement.executeQuery();
            SchoolDao sDao = new SchoolDao();
            if (rSet.next()) {
                classNum.setClass_num(rSet.getString("class_num"));
                classNum.setSchool(sDao.get(rSet.getString("school_cd")));
            } else {
                classNum = null;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
        }
        return classNum;
    }

    public List<String> filter(School school) throws Exception {
        List<String> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("select class_num from class_num where school_cd=? order by class_num");
            statement.setString(1, school.getCd());
            ResultSet rSet = statement.executeQuery();
            while (rSet.next()) {
                list.add(rSet.getString("class_num"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
        }
        return list;
    }

    // 【追加】クラス番号を新規登録するメソッド
    public boolean save(ClassNum classNum) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                "insert into class_num (class_num, school_cd) values (?, ?)"
            );
            statement.setString(1, classNum.getClass_num());
            statement.setString(2, classNum.getSchool().getCd());
            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
        }
    }

    // 【追加】既存のクラス番号を更新するメソッド
    public boolean save(ClassNum classNum, String newClassNum) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                "update class_num set class_num=? where class_num=? and school_cd=?"
            );
            statement.setString(1, newClassNum);
            statement.setString(2, classNum.getClass_num());
            statement.setString(3, classNum.getSchool().getCd());
            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
        }
    }
}
