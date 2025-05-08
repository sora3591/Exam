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

public class SubjectUpdateExecuteAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		 HttpSession session = req.getSession();

		   Teacher teacher = (Teacher) session.getAttribute("user");  // セッションから教師情報を取得
		   Map<String, String> errors = new HashMap<>();


		     // フォームからの入力データを取得

		     String subject_cd = req.getParameter("cd");

		     String subject_name = req.getParameter("name");

		     SubjectDao subjectDao=new SubjectDao();

		     Subject subject=new Subject();


		     List<Subject> allSubjects = subjectDao.filter(teacher.getSchool());
		     String subjectcd_list[]={};
		     boolean found = false;
			for (Subject s : allSubjects) {
		            if (s.getCd().equals(subject_cd)) {
		                found = true;
		                break;
		            }
		        }

		        if (!found) {
		            errors.put("f1", "科目コードが存在していません");
		            req.setAttribute("errors", errors);
		            req.setAttribute("no", subject_cd);
		            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
		            return;
		        }




		     subject.setCd(subject_cd);
		     subject.setName(subject_name);
		     subject.setSchool(teacher.getSchool());




		     boolean sava =subjectDao.save(subject);

		     if (sava) {
	        	 session.removeAttribute("errors");

	             session.setAttribute("successMessage", "登録が完了しました");

	             res.sendRedirect("subject_update_done.jsp");  // 学生登録完了ページにリダイレクト

	         } else {

	             // 保存が失敗した場合の処理

	             session.setAttribute("errorMessage", "登録が失敗しました");

	             res.sendRedirect("subject_update.jsp");  // 登録フォームにリダイレクト

	         }
	}
	}
