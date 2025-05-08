package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String subjectCdStr = "";
        String subjectName = "";
        int subjectCd = 0;
        List<Subject> subjects = new ArrayList<>();
        SubjectDao cDao = new SubjectDao();
        Map<String, String> errors = new HashMap<>();

        subjectCdStr = req.getParameter("f1");
        subjectName = req.getParameter("f2");



        if (subjectCdStr != null && !subjectCdStr.isEmpty()) {
            try {
                subjectCd = Integer.parseInt(subjectCdStr);
            } catch (NumberFormatException e) {
                errors.put("subjectCd", "科目コードは数字で入力してください");
            }
        }

        // 全件取得
        List<Subject> allSubjects = cDao.filter(teacher.getSchool());


        // Java側でフィルター処理
        for (Subject subject : allSubjects) {
            boolean matches = true;

            // 科目コードによるフィルター（部分一致）
            if (subjectCd != 0) {
                try {
                    int subjectCdFromDb = Integer.parseInt(subject.getCd());
                    if (subjectCdFromDb != subjectCd) {
                        matches = false;
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
            }

            // 科目名によるフィルター（部分一致）
            if (subjectName != null && !subjectName.isEmpty() && !subjectName.equals("0")) {
                if (!subject.getName().contains(subjectName)) {
                    matches = false;
                }
            }

            if (matches) {
                subjects.add(subject);
            }
        }


        // 属性をセット
        req.setAttribute("f1", subjectCdStr);
        req.setAttribute("f2", subjectName);
        req.setAttribute("subjects", subjects);
        req.setAttribute("errors", errors);

        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}
