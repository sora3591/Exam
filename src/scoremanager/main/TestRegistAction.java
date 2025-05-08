package scoremanager.main;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String classNum = "";
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();
        ClassNumDao cNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        int entYear = 0;
        int times = 0;
        TestDao testDao = new TestDao();
        StudentDao studentDao = new StudentDao();

        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i < year + 1; i++) {
            entYearSet.add(i);
        }

        List<String> classNumList = cNumDao.filter(teacher.getSchool());
        List<Subject> subjectList = subjectDao.filter(teacher.getSchool());
        List<String> subjectNames = new ArrayList<>();

        for (Subject subject : subjectList) {
            subjectNames.add(subject.getName());
        }

        req.setAttribute("subjectname", subjectNames);
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumList);

        if (req.getParameter("f1") != null) {
            entYear = Integer.parseInt(req.getParameter("f1"));
        }
        if (req.getParameter("f4") != null) {
            times = Integer.parseInt(req.getParameter("f4"));
        }

        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectNames);
        req.setAttribute("f4", times);

        if (req.getParameter("f1") != null) {
            List<Student> students = null;
            boolean isAttend = true;
            int entYear2 = Integer.parseInt(req.getParameter("f1"));
            String classNum2 = req.getParameter("f2");
            students = studentDao.filter(teacher.getSchool(), entYear2, classNum2, isAttend);
            req.setAttribute("student", students);
        }

        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}
