package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");
        String studentNo = req.getParameter("f4");

        int entYear = 0;
        Map<String, String> errors = new HashMap<>();

        if (entYearStr != null && !entYearStr.equals("0")) {
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (NumberFormatException e) {
                errors.put("f1", "入学年度は数値を入力してください");
            }
        }

        if (classNum == null || classNum.equals("0")) {
            classNum = "";
        }
        if (subjectCd == null) {
            subjectCd = "";
        }
        if (studentNo == null) {
            studentNo = "";
        }

        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();
        ClassNumDao cNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        TestListStudentDao testDao = new TestListStudentDao();
        List<TestListStudent> testList = null;

        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i < year + 1; i++) {
            entYearSet.add(i);
        }
        List<String> classNumList = cNumDao.filter(teacher.getSchool());
        List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

        boolean searched = entYear != 0 || !classNum.isEmpty() || !subjectCd.isEmpty() || !studentNo.isEmpty();
        req.setAttribute("searched", searched);

        if (subjectCd != null && !subjectCd.isEmpty() && !subjectCd.equals("0")) {
            for (Subject sub : subjectList) {
                if (sub.getCd().equals(subjectCd)) {
                    req.setAttribute("subjectName", sub.getName());
                    break;
                }
            }
        }

        if (searched && errors.isEmpty()) {
            try {
                testList = testDao.filter(entYear, classNum, subjectCd, studentNo, teacher.getSchool());
            } catch (Exception e) {
                errors.put("filter", "検索中にエラーが発生しました。");
                e.printStackTrace();
            }
        }

        req.setAttribute("errors", errors);
        req.setAttribute("testList", testList);
        req.setAttribute("f1", entYearStr != null ? entYearStr : "0");
        req.setAttribute("f2", classNum != null ? classNum : "0");
        req.setAttribute("f3", subjectCd != null ? subjectCd : "");
        req.setAttribute("f4", studentNo != null ? studentNo : "");

        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumList);
        req.setAttribute("subject_list", subjectList);

        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}