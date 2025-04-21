package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;

public class SubjectDao extends Dao{


	private String baseSql="select * from subject where school_cd=?";

	public Subject get(String cd,School school)throws Exception{
		// 教員インスタンスを初期化
				Subject subject = new Subject();
				// コネクションを確立
				Connection connection = getConnection();
				// プリペアードステートメント
				PreparedStatement statement = null;

				try {
					// プリペアードステートメントにSQL文をセット
					statement = connection.prepareStatement("select * from subject where id=?");
					// プリペアードステートメントに教員IDをバインド
					statement.setString(1, cd);
					// プリペアードステートメントを実行
					ResultSet resultSet = statement.executeQuery();

					// 学校Daoを初期化
					SchoolDao schoolDao = new SchoolDao();


					if (resultSet.next()) {
						// リザルトセットが存在する場合
						// 教員インスタンスに検索結果をセット
						subject.setCd(resultSet.getString("cd"));
						subject.setName(resultSet.getString("name"));
						subject.setSchool(resultSet.getString("scjool_cd"));
						// 学校フィールドには学校コードで検索した学校インスタンスをセット
						subject.setSchool(schoolDao.get(resultSet.getString("school_cd")));
					} else {
						// リザルトセットが存在しない場合
						// 教員インスタンスにnullをセット
						teacher = null;
					}
				} catch (Exception e) {
					throw e;
				} finally {
					// プリペアードステートメントを閉じる
					if (statement != null) {
						try {
							statement.close();
						} catch (SQLException sqle) {
							throw sqle;
						}
					}
					// コネクションを閉じる
					if (connection != null) {
						try {
							connection.close();
						} catch (SQLException sqle) {
							throw sqle;
						}
					}
				}

				return teacher;
			}

				}

	public List<Subject> filter(School school) throws Exception{

	}

	public boolean save(Subject subject) throws Exception{

	}

	public boolean delete(Subject subject) throws Exception{

	}


}
