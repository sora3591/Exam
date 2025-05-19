package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class ClassUpdateExecuteAction extends Action{
	 @Override
		public void execute(HttpServletRequest req,HttpServletResponse res)throws Exception{
		 	HttpSession session=req.getSession();
		 	Teacher teacher = (Teacher)session.getAttribute("user");
		 	boolean isAttend=true;

		 	List<Student>students=null;
		 	String classnum=req.getParameter("101");
		 	StudentDao sDao=new StudentDao();






		 	req.getRequestDispatcher("class_update_done.jsp").forward(req, res);




}
}