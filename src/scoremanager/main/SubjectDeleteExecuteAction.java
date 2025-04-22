package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action{
	 public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		 HttpSession session = req.getSession();

		   Teacher teacher = (Teacher) session.getAttribute("user");  // セッションから教師情報を取得

		     // フォームからの入力データを取得

		     String subject_cd = req.getParameter("subject_cd");  // 学生番号

		     String subject_name = req.getParameter("subject_name");  // 氏名

		     SubjectDao subjectDao=new SubjectDao();

		     Subject subject=new Subject();

		     subject.setCd(subject_cd);
		     subject.setName(subject_name);

		     boolean delete=subjectDao.delete(subject);

		     if (delete) {
	        	 session.removeAttribute("errors");

	             session.setAttribute("successMessage", "登録が完了しました");

	             res.sendRedirect("subject_delete_done.jsp");  // 学生登録完了ページにリダイレクト

	         } else {

	             // 保存が失敗した場合の処理

	             session.setAttribute("errorMessage", "登録が失敗しました");

	             res.sendRedirect("subject_list.jsp");  // 登録フォームにリダイレクト

	         }

}
}
