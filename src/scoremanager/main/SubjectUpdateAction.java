package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action{
	public void execute(HttpServletRequest req,HttpServletResponse res)throws Exception{
		HttpSession session=req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		 Map<String, String> errors = new HashMap<>();


		String no = req.getParameter("no");

		SubjectDao subjectDao=new SubjectDao();

		List<Subject> list = subjectDao.filter(teacher.getSchool());

		req.setAttribute("subject", list);
		req.setAttribute("no", no);

		 



		req.getRequestDispatcher("subject_update.jsp").forward(req, res);
	}
}
