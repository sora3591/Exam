package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");

		//student_list.jspから変更を押した学生番号を取得
		String no = req.getParameter("no");

		String isAttendStr="";//入力された在学生フラグ

		isAttendStr = req.getParameter("f3");

		//学生Daoを初期化
		StudentDao StudentDao = new StudentDao();
		//学生番号をもとに学生の情報を取得
		Student student = StudentDao.get(no);

		//クラス番号Daoを初期化
		ClassNumDao cNumDao = new ClassNumDao();
		//ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list = cNumDao.filter(teacher.getSchool());

		//リクエストにデータをセット
		req.setAttribute("f3", isAttendStr);
		req.setAttribute("student", student);
		req.setAttribute("class_num_set", list);



		//指定されたJSPページ("student_update.jsp")へフォワード
		req.getRequestDispatcher("student_update.jsp").forward(req, res);
	}
}