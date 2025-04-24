package scoremanager.main;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	    HttpSession session = req.getSession();
	    Teacher teacher = (Teacher) session.getAttribute("user");

	    String classNum = req.getParameter("class_num");
	    School school = teacher.getSchool();
	    int no = Integer.parseInt(req.getParameter("no"));
	    int point = Integer.parseInt(req.getParameter("point"));

	    Map<String, String> errors = new HashMap<>();
	    TestDao testDao = new TestDao();

	    if (point < 0 || point > 100) {
	        errors.put("f1", "0～100の範囲で入力してください");
	        session.setAttribute("errors", errors);
	        res.sendRedirect("TestRegist.action");
	        return;

	    } else {
	        Student student = new Student();
	        Subject subject = new Subject();
	        Test test = new Test();
	        test.setStudent(student);
	        test.setClassNum(classNum);
	        test.setSubject(subject);
	        test.setSchool(school);
	        test.setNo(no);

	        // Connection を取得
	        Connection connection = testDao.getConnection();  // ここで接続を取得

	        try {
	            boolean isSaved = testDao.save(test, connection);  // Connection を渡して save メソッドを呼ぶ

	            if (isSaved) {
	                session.removeAttribute("errors");
	                session.setAttribute("successMessage", "登録が完了しました");
	                res.sendRedirect("test_regist_done.jsp");
	            } else {
	                session.setAttribute("errorMessage", "登録が失敗しました");
	                res.sendRedirect("test_regist.jsp");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            session.setAttribute("errorMessage", "登録中にエラーが発生しました");
	            res.sendRedirect("test_regist.jsp");
	        }
	    }
	}
}
