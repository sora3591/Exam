package scoremanager.main;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class ClassUpdateAction extends Action{
    @Override
	public void execute(HttpServletRequest req,HttpServletResponse res)throws Exception{
		HttpSession session=req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		
		StudentDao sDao=new StudentDao();
		



		req.getRequestDispatcher("class_update.jsp").forward(req, res);


}
}