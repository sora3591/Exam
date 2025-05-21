package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class UserListAction extends Action{
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        Teacher teachers = new Teacher();

		TeacherDao teacherDao = new TeacherDao();

		String name = req.getParameter(teachers.getName());

		System.out.println(name);


        req.getRequestDispatcher("user_list").forward(req, res);

}
}