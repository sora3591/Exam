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

public class StudentCreateAction extends Action{
	@Override
	public void execute(HttpServletRequest req,HttpServletResponse res)throws Exception{
		HttpSession session=req.getSession();
		Teacher teacher=(Teacher)session.getAttribute("user");


		ClassNumDao cNumDao=new ClassNumDao();//クラス番号Daoを初期化
		LocalDate todaysDate=LocalDate.now();//LocalDateインスタンスを取得
		int year=todaysDate.getYear();//現在の年を取得





		//リストを初期化
		List<Integer>entYearSet = new ArrayList<>();
		//１０年前から１年後まで年をリストに追加
		for(int i=year-10; i<year+10;i++){
			entYearSet.add(i);
		}
		//DBからデータを取得３
		//ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list=cNumDao.filter(teacher.getSchool());



		//リクエストにデータをセット
				req.setAttribute("class_num_set",list);
				req.setAttribute("ent_year_set", entYearSet);
				//JSPへフォワード７
				req.getRequestDispatcher("student_create.jsp").forward(req, res);
	}
}
