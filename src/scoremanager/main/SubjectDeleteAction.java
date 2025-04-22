package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteAction extends Action{
	@Override
	public void execute(HttpServletRequest req,HttpServletResponse res)throws Exception{
		HttpSession session=req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		SubjectDao subjectDao=new SubjectDao();

		School school = teacher.getSchool();


		String no = req.getParameter("no");

		Subject subject=subjectDao.get(no, teacher.getSchool());

		List<Subject> list = subjectDao.filter(teacher.getSchool());

		req.setAttribute("subject", subject);


		req.getRequestDispatcher("subject_delete.jsp").forward(req, res);



	}
}

