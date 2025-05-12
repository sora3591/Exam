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
import tool.Action;

public class TestRegistAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();

        ClassNumDao cNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        StudentDao studentDao = new StudentDao();

        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i < year + 1; i++) {
            entYearSet.add(i);
        }

        List<String> classNumList = cNumDao.filter(teacher.getSchool());
        List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumList);
        req.setAttribute("subject_list", subjectList); // 修正：Subjectリストを渡す

        // フォームからの入力値取得
        String f1Str = req.getParameter("f1");
        String f2 = req.getParameter("f2");
        String f3 = req.getParameter("f3");
        String f4Str = req.getParameter("f4");

        int f1 = (f1Str != null && !f1Str.equals("0")) ? Integer.parseInt(f1Str) : 0;
        int f4 = (f4Str != null && !f4Str.equals("0")) ? Integer.parseInt(f4Str) : 0;

        req.setAttribute("f1", f1);
        req.setAttribute("f2", f2);
        req.setAttribute("f3", f3);
        req.setAttribute("f4", f4);

        boolean searchPerformed = f1Str != null || f2 != null || f3 != null || f4Str != null;
        req.setAttribute("searchPerformed", searchPerformed);

        boolean filterError = (f1 == 0 || f2 == null || f2.equals("0") || f3 == null || f3.equals("0") || f4 == 0);
        req.setAttribute("filterError", filterError);

        if (filterError) {
            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        }

        List<Student> students = studentDao.filter(teacher.getSchool(), f1, f2, true);
        req.setAttribute("student", students);

        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}
