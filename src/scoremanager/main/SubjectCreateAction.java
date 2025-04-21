package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateAction extends Action{
	@Override
	public void execute(HttpServletRequest req,HttpServletResponse res)throws Exception{
		HttpSession session=req.getSession();
		Teacher teacher=(Teacher)session.getAttribute("user");

		SubjectDao cDao = new SubjectDao();

		List<Subject> allSubjects = cDao.filter(teacher.getSchool());

		req.setAttribute("f1", allSubjects);

		req.getRequestDispatcher("subject_create.jsp").forward(req, res);
	}
}
