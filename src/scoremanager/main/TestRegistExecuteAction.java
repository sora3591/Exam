package scoremanager.main;

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
            session.setAttribute("errors", errors);  // セッションにエラーを保存
            res.sendRedirect("TestRegist.action");  // 登録フォームにリダイレクト
            return;  // 処理終了

		}else{
			Student student = new Student();

			Subject subject = new Subject();

			Test test = new Test();

			test.setStudent(student);

			test.setClassNum(classNum);

			test.setSubject(subject);

			test.setSchool(school);

			test.setNo(no);

			test.setNo(no);

			boolean isSaved = testDao.save(student);

			if (isSaved) {
            	session.removeAttribute("errors");

                session.setAttribute("successMessage", "登録が完了しました");

                res.sendRedirect("test_regist_done.jsp");  // 学生登録完了ページにリダイレクト

            } else {

                // 保存が失敗した場合の処理

                session.setAttribute("errorMessage", "登録が失敗しました");

                res.sendRedirect("test_regist.jsp");  // 登録フォームにリダイレクト

            }
        }
	}
}
