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

        // 入力値取得
        String subjectName = req.getParameter("subject_name");
        String timesStr = req.getParameter("times");
        String classNum = req.getParameter("class_num");
        String entYearStr = req.getParameter("ent_year");

        // times の検証
        int times = 0;
        if (timesStr == null || timesStr.trim().isEmpty()) {
            errors.put("times", "回数が入力されていません。");
        } else {
            try {
                times = Integer.parseInt(timesStr.trim());
            } catch (NumberFormatException e) {
                errors.put("times", "回数は数値で入力してください。");
            }
        }

        // entYear の検証
        int entYear = 0;
        if (entYearStr == null || entYearStr.trim().isEmpty()) {
            errors.put("entYear", "入学年度が入力されていません。");
        } else {
            try {
                entYear = Integer.parseInt(entYearStr.trim());
            } catch (NumberFormatException e) {
                errors.put("entYear", "入学年度は数値で入力してください。");
            }
        }

        // 学生・科目情報取得
        StudentDao studentDao = new StudentDao();
        SubjectDao subjectDao = new SubjectDao();
        List<Student> students = studentDao.filter(school, entYear, classNum, true);
        Subject subject = subjectDao.get(subjectName, school);

        List<Test> testList = new ArrayList<>();

        for (Student student : students) {
            String paramName = "point_" + student.getNo();
            String pointStr = req.getParameter(paramName);

            if (pointStr == null || pointStr.trim().isEmpty()) {
                errors.put(paramName, "学生番号 " + student.getNo() + " の点数が入力されていません。");
                continue;
            }

            try {
                int point = Integer.parseInt(pointStr.trim());
                if (point < 0 || point > 100) {
                    errors.put(paramName, "学生番号 " + student.getNo() + " の点数は0～100の範囲で入力してください。");
                    continue;
                }

                Test test = new Test();
                test.setStudent(student);
                test.setSubject(subject);
                test.setSchool(school);
                test.setNo(times);
                test.setPoint(point);
                test.setClassNum(classNum);
                testList.add(test);

            } catch (NumberFormatException e) {
                errors.put(paramName, "学生番号 " + student.getNo() + " の点数が無効な数値です。");
            }
        }

        // 保存処理
        try {
            TestDao testDao = new TestDao();
            testDao.save(testList);
        } catch (Exception e) {
            errors.put("database", "成績の保存中にエラーが発生しました。");
            session.setAttribute("errors", errors);
            return;
        }

        // 正常時処理
        session.setAttribute("successMessage", "登録が完了しました。");
        res.sendRedirect("test_regist_done.jsp");
    }
}
