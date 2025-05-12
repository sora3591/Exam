package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassCreateExecuteAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        ClassNumDao cDao = new ClassNumDao();
        ClassNum classnums=new ClassNum();

        Teacher teacher = (Teacher) session.getAttribute("user");

        String classnum = req.getParameter("classnum");

        classnums.setSchool(teacher.getSchool());
        classnums.setClass_num(classnum);

        cDao.save(classnums);

        req.getRequestDispatcher("class_create_done.jsp").forward(req, res);


}
}