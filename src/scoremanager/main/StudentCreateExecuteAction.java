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

        String no = req.getParameter("no");  // 学生番号

        String name = req.getParameter("name");  // 氏名

        int entYear = Integer.parseInt(req.getParameter("ent_year"));  // 入学年度

        String classNum = req.getParameter("class_num");  // クラス番号

        boolean isAttend = true;  // 仮に出席していると設定（実際には適宜変更）

        Map<String, String> errors = new HashMap<>();

        // 学生情報をデータベースに保存

        StudentDao studentDao = new StudentDao();

        // 入学年度が未選択の場合

        if (entYear == 0) {


            errors.put("f1", "入学年度を選択してください");

            session.setAttribute("errors", errors);  // セッションにエラーを保存

            res.sendRedirect("StudentCreate.action");  // 登録フォームにリダイレクト

        } else {

            // 学生番号の重複チェック

            if (studentDao.get(no) != null) {

                errors.put("f2", "学生番号が重複しています");

                session.setAttribute("errors", errors);  // セッションにエラーを保存

                res.sendRedirect("StudentCreate.action");  // 登録フォームにリダイレクト

            } else {

                // 学生情報を作成

                Student student = new Student();

                student.setNo(no);

                student.setName(name);

                student.setEntYear(entYear);

                student.setClassNum(classNum);

                student.setAttend(isAttend);

                student.setSchool(teacher.getSchool());  // 学校情報は教師から取得

                // 学生情報をデータベースに保存

                boolean isSaved = studentDao.save(student);  // 学生情報の保存

                // 保存が成功した場合の処理

                if (isSaved) {
                	session.removeAttribute("errors");

                    session.setAttribute("successMessage", "登録が完了しました");

                    res.sendRedirect("student_create_done.jsp");  // 学生登録完了ページにリダイレクト

                } else {

                    // 保存が失敗した場合の処理

                    session.setAttribute("errorMessage", "登録が失敗しました");

                    res.sendRedirect("student_create.jsp");  // 登録フォームにリダイレクト

                }

            }

        }

    }

}
