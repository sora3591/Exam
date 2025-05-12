package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassCreateAction extends Action{
	public void execute(HttpServletRequest req,HttpServletResponse res)throws Exception{
		HttpSession session=req.getSession();
		Teacher teacher=(Teacher)session.getAttribute("user");

		ClassNumDao cDao = new ClassNumDao();

		List<String> allSubjects = cDao.filter(teacher.getSchool());

		req.setAttribute("f1", allSubjects);

		req.getRequestDispatcher("class_create.jsp").forward(req, res);
	}
}

