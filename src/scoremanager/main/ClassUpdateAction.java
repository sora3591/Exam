
package scoremanager.main;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassUpdateAction extends Action{
    @Override
	public void execute(HttpServletRequest req,HttpServletResponse res)throws Exception{
		HttpSession session=req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		String classNum = req.getParameter("classnum");
		String no =req.getParameter("no");

		ClassNumDao classNumDao = new ClassNumDao();

        List<String> list = classNumDao.filter(teacher.getSchool());





        req.setAttribute("no", no);
        req.setAttribute("classnum", classNum);




		req.getRequestDispatcher("class_update.jsp").forward(req, res);


}
}

