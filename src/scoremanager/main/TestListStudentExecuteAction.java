package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.TestListStudent;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Map<String, String> errors = new HashMap<>();
		String stunum = req.getParameter("name");
		if (stunum == null) {
        	errors.put("f1", "学生番号が入力されてません");

            session.setAttribute("errors", errors);  // セッションにエラーを保存

            res.sendRedirect("Testlist.action");
		}
		else {
	        List<TestListStudent> list = new ArrayList<>();
			TestListStudent testListStudent = new TestListStudent();
			Student student = new Student();
			TestListStudentDao testListStudentDao = new TestListStudentDao();
			student.setNo(stunum);
			list = testListStudentDao.filter(student);
			 session.setAttribute("testListStudents",list);
			 res.sendRedirect("test_list.jsp");
		}
	}



}
