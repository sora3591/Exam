package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        Map<String, String> errors = new HashMap<>();
        String subjectCd = req.getParameter("f3"); // ← f3 から subjectCd を取得
        String timesStr = req.getParameter("f4");
        String classNum = req.getParameter("f2");
        String entYearStr = req.getParameter("f1");

        int times = Integer.parseInt(timesStr);
        int entYear = Integer.parseInt(entYearStr);

        StudentDao studentDao = new StudentDao();
        SubjectDao subjectDao = new SubjectDao();
        List<Student> students = studentDao.filter(school, entYear, classNum, true);
        Subject subject = subjectDao.get(subjectCd, school); // ← 修正ポイント

        List<Test> testList = new ArrayList<>();

        for (Student student : students) {
            String paramName = "point_" + student.getNo();
            String pointStr = req.getParameter(paramName);

            if (pointStr != null && !pointStr.trim().isEmpty()) {
                int point = Integer.parseInt(pointStr.trim());
                Test test = new Test();
                test.setStudent(student);
                test.setSubject(subject);
                test.setSchool(school);
                test.setNo(times);
                test.setPoint(point);
                test.setClassNum(classNum);

                testList.add(test);
            }
        }

        if (!errors.isEmpty()) {
            session.setAttribute("errors", errors);
            res.sendRedirect("test_regist.jsp");
            return;
        }

        TestDao testDao = new TestDao();
        testDao.save(testList);

        session.setAttribute("successMessage", "登録が完了しました。");
        req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
    }
}
