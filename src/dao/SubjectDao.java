package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao{
	private String baseSql="select * from subject where school_cd=?";
	public Subject get(String subject_cd,School school)throws Exception{
		//クラス番号インスタンスを初期化
				Subject subjectcd =new Subject();
				//データベースへのコネクションを確立
				Connection connection =getConnection();
				//プリペアードステートメント
				PreparedStatement statement=null;

				try {
					// プリペアードステートメントにSQL文をセット
					statement = connection.prepareStatement("select * from subject where subject_cd=?");
					// プリペアードステートメントに教員IDをバインド
					statement.setString(1, subject_cd);
					// プリペアードステートメントを実行
					ResultSet resultSet = statement.executeQuery();


					}
	}

}
