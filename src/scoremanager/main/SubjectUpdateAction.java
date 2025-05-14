package scoremanager.main;

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

public class SubjectUpdateAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        Map<String, String> errors = new HashMap<>();

        String no = req.getParameter("no");

        SubjectDao subjectDao = new SubjectDao();

        // 科目一覧の取得（セレクトボックス用）
        List<Subject> list = subjectDao.filter(teacher.getSchool());

        // 選択された科目コードに対応する科目名を取得
        Subject selectedSubject = subjectDao.get(no, teacher.getSchool());

        req.setAttribute("subject", list);
        req.setAttribute("no", no);

        if (selectedSubject != null) {
            req.setAttribute("name", selectedSubject.getName()); // ← 科目名をセット
        }

        req.getRequestDispatcher("subject_update.jsp").forward(req, res);
    }
}
