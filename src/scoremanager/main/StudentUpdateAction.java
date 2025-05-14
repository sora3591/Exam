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

	    HttpSession session = req.getSession();
	    Teacher teacher = (Teacher) session.getAttribute("user");

	    // student_list.jspから変更を押した学生番号を取得
	    String no = req.getParameter("no");

	    // 学生Daoから対象の学生を取得
	    StudentDao studentDao = new StudentDao();
	    Student student = studentDao.get(no);

	    // クラス一覧を取得
	    ClassNumDao cNumDao = new ClassNumDao();
	    List<String> classNumList = cNumDao.filter(teacher.getSchool());

	    // 取得した学生情報をリクエスト属性に設定
	    req.setAttribute("student", student);
	    req.setAttribute("no", student.getNo());
	    req.setAttribute("name", student.getName());
	    req.setAttribute("class_num", student.getClassNum());
	    req.setAttribute("ent_year", student.getEntYear());

	    // チェックボックスのために在学中フラグもセット（f3という属性名で）
	    if (student.isAttend()) {
	        req.setAttribute("f3", "checked");  // JSPでは empty かどうかで判定
	    }

	    // クラス番号一覧をセット
	    req.setAttribute("class_num_set", classNumList);

	    // JSPへフォワード
	    req.getRequestDispatcher("student_update.jsp").forward(req, res);
	}
}