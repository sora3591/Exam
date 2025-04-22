package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();

        Teacher teacher = (Teacher) session.getAttribute("user");

        String cd = req.getParameter("cd");

        String name = req.getParameter("name");

        School school = (School) session.getAttribute("school");

        Map<String, String> errors = new HashMap<>();

        SubjectDao subjectDao = new SubjectDao();

        System.out.println(teacher.getSchool());




        if (cd == null || cd.length() != 3) {
        	errors.put("f1", "科目コードは3文字で入力してください");

            session.setAttribute("errors", errors);  // セッションにエラーを保存

            res.sendRedirect("SubjectCreate.action");  // 登録フォームにリダイレクト

        } else {
        	if (subjectDao.get(cd, school) != null) {

                errors.put("f2", "科目コードが重複しています");

                req.setAttribute("errors", errors);
                req.getRequestDispatcher("subject_create.jsp").forward(req, res);
                return;




            } else {

            	Subject subject = new Subject();

            	subject.setCd(cd);

            	subject.setName(name);

            	subject.setSchool(teacher.getSchool());

            	boolean isSaved = subjectDao.save(subject);

            	if (isSaved) {
                	session.removeAttribute("errors");

                    session.setAttribute("successMessage", "登録が完了しました");

                    res.sendRedirect("subject_create_done.jsp");
                } else {

                    // 保存が失敗した場合の処理

                    session.setAttribute("errorMessage", "登録が失敗しました");

                    res.sendRedirect("SubjectCreate.action");

                }
            }
        }
	}
}
