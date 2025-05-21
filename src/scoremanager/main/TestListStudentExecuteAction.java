package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {





		// セッションからユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");





		String entYearStr = "";// 入力された入学年度
		String classNum1 = "";// 入力されたクラス番号
		int entYear = 0;// 入学年度
		LocalDate todaysDate = LocalDate.now();// LocalDateインスタンスを取得
		int year1 = todaysDate.getYear();// 現在の年を取得
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		SubjectDao subjectDao = new SubjectDao();
		int times = 0; // 回数

		List<Integer> entYearSet = new ArrayList<>();
		// １０年前から１年後まで年をリストに追加
		for (int i = year1 - 10; i < year1 + 1; i++) {
			entYearSet.add(i);
		}
		System.out.println(cNumDao.filter(teacher.getSchool()));

		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list1 = cNumDao.filter(teacher.getSchool());

		List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

		// 科目名だけを取り出す（必要なら）
		List<Subject> subjectNames = subjectDao.filter(teacher.getSchool());

//		for (Subject subject : subjectList) {
//			subjectNames.add(subject.getName());
//		}

		req.setAttribute("subjectName", subjectNames);

		// リクエストに入学年度をセット
		req.setAttribute("f1", entYear);
		// リクエストにクラス番号をセット
		req.setAttribute("f2", classNum1);

		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", list1);













		Map<String, String> errors = new HashMap<>();
		String stunum = req.getParameter("name");
		if (stunum == null) {
        	errors.put("f1", "学生番号が入力されてません");

            session.setAttribute("errors", errors);  // セッションにエラーを保存

            res.sendRedirect("TestList.action");
		}
		else {
	        List<TestListStudent> list = new ArrayList<>();
			TestListStudent testListStudent = new TestListStudent();
			Student student = new Student();
			TestListStudentDao testListStudentDao = new TestListStudentDao();
			student.setNo(stunum);
			list = testListStudentDao.filter(student);

			 session.setAttribute("list",list);


			 req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
		}
	}



}
