package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassListAction extends Action{
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        ClassNumDao cNumDao=new ClassNumDao();//クラス番号Daoを初期化
        List<String> classNums=new ArrayList<>();
        List<String> allclassnum = cNumDao.filter(teacher.getSchool());
        for (String classnum : allclassnum) {

        	classNums.add(classnum);

        }




        req.setAttribute("class_num",classNums);
        req.getRequestDispatcher("class_list.jsp").forward(req, res);

}
}
