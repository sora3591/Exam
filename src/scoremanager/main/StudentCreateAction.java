package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class StudentCreateAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // クラス番号の取得
        ClassNumDao cNumDao = new ClassNumDao();
        List<String> classNumList = cNumDao.filter(teacher.getSchool());

        // 入学年度の候補リスト生成（過去10年～未来9年）
        int currentYear = LocalDate.now().getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = currentYear - 10; i < currentYear + 10; i++) {
            entYearSet.add(i);
        }

        // セッションからフォームの入力値とエラーを取得
        if (session.getAttribute("errors") != null) {
            req.setAttribute("errors", session.getAttribute("errors"));
            session.removeAttribute("errors");
        }

        if (session.getAttribute("form_no") != null) {
            req.setAttribute("no", session.getAttribute("form_no"));
            req.setAttribute("name", session.getAttribute("form_name"));
            req.setAttribute("class_num", session.getAttribute("form_classNum"));
            req.setAttribute("ent_year", session.getAttribute("form_entYear"));
            session.removeAttribute("form_no");
            session.removeAttribute("form_name");
            session.removeAttribute("form_classNum");
            session.removeAttribute("form_entYear");
        }

        // フォワード用のリストをセット
        req.setAttribute("class_num_set", classNumList);
        req.setAttribute("ent_year_set", entYearSet);

        // JSPへフォワード
        req.getRequestDispatcher("student_create.jsp").forward(req, res);
    }
}
