package scoremanager.main;




import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassUpdateExecuteAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		 HttpSession session = req.getSession();

		   Teacher teacher = (Teacher) session.getAttribute("user");  // セッションから教師情報を取得
		   Map<String, String> errors = new HashMap<>();


		     // フォームからの入力データを取得

		     String classNum = req.getParameter("classnum");

		     ClassNumDao classnumDao=new ClassNumDao();

		     ClassNum classNums=new ClassNum();



		     classNums.setClass_num(req.getParameter("no"));
		     classNums.setSchool(teacher.getSchool());


		     req.setAttribute("class_num", classNum);





		     boolean sava =classnumDao.save(classNums, classNum);
		     if (sava) {
	        	 session.removeAttribute("errors");

	             session.setAttribute("successMessage", "変更が完了しました");

	             res.sendRedirect("class_update_done.jsp");  // 学生登録完了ページにリダイレクト

	         } else {

	             // 保存が失敗した場合の処理

	             session.setAttribute("errorMessage", "変更が失敗しました");

	             res.sendRedirect("class_list.jsp");  // 登録フォームにリダイレクト

	         }
	}
}
