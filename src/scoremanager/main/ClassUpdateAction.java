package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassUpdateAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String classnum = req.getParameter("classnum");

        if (classnum != null && !classnum.isEmpty()) {
            ClassNumDao cNumDao = new ClassNumDao();
            ClassNum classNum = cNumDao.get(classnum, teacher.getSchool());

            if (classNum != null) {
                req.setAttribute("classnum", classNum.getClass_num());
            } else {
                req.setAttribute("error", "クラス情報が存在しませんでした。");
            }
        }

        req.getRequestDispatcher("class_update.jsp").forward(req, res);
    }
}
