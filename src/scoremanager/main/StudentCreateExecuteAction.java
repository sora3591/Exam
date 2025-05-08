package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentCreateExecuteAction extends Action {

    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();

        Teacher teacher = (Teacher) session.getAttribute("user");  // セッションから教師情報を取得

        // フォームからの入力データを取得
        String no = req.getParameter("no");       // 学生番号
        String name = req.getParameter("name");   // 氏名
        String classNum = req.getParameter("class_num");  // クラス番号
        int entYear = 0;

        try {
            entYear = Integer.parseInt(req.getParameter("ent_year"));
        } catch (NumberFormatException e) {
            // 無効な値（nullや数字以外）
            entYear = 0;
        }

        boolean isAttend = true;

        Map<String, String> errors = new HashMap<>();

        // 入学年度チェック
        if (entYear == 0) {
            errors.put("f1", "入学年度を選択してください");
        }

        // 学生番号チェック
        if (no == null || no.trim().isEmpty()) {
            errors.put("f2", "学生番号を入力してください");
        } else {
            StudentDao studentDao = new StudentDao();
            if (studentDao.get(no) != null) {
                errors.put("f2", "学生番号が重複しています");
            }
        }

        // バリデーションエラーがある場合はリダイレクト
        if (!errors.isEmpty()) {
            session.setAttribute("errors", errors);
            session.setAttribute("form_no", no);
            session.setAttribute("form_name", name);
            session.setAttribute("form_classNum", classNum);
            session.setAttribute("form_entYear", entYear);
            res.sendRedirect("StudentCreate.action");
            return;
        }

        // 学生情報を作成して保存
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(isAttend);
        student.setSchool(teacher.getSchool());

        StudentDao studentDao = new StudentDao();
        boolean isSaved = studentDao.save(student);

        if (isSaved) {
            session.removeAttribute("errors");
            session.setAttribute("successMessage", "登録が完了しました");
            res.sendRedirect("student_create_done.jsp");
        } else {
            session.setAttribute("errorMessage", "登録が失敗しました");
            res.sendRedirect("student_create.jsp");
        }
    }
}
