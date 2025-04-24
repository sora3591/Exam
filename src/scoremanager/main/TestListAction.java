package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class TestListAction extends Action{
	@Override
	public void execute(HttpServletRequest req,HttpServletResponse res)throws Exception{
		HttpSession session=req.getSession();
		String entYearStr="";//入力された入学年度
		String classNum="";//入力されたクラス番号
		Teacher teacher=(Teacher)session.getAttribute("user");
		LocalDate todaysDate=LocalDate.now();//LocalDateインスタンスを取得
		int year=todaysDate.getYear();//現在の年を取得
		ClassNumDao cNumDao=new ClassNumDao();//クラス番号Daoを初期化
		SubjectDao subjectDao=new SubjectDao();



		List<Integer>entYearSet = new ArrayList<>();
		//１０年前から１年後まで年をリストに追加
		for(int i=year-10; i<year+1;i++){
			entYearSet.add(i);
		}
		//DBからデータを取得３
		//ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list=cNumDao.filter(teacher.getSchool());

		List<Subject> list2=subjectDao.filter(teacher.getSchool());
		System.out.println(list2);





		req.setAttribute("class_num_set",list);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("subjects", list2);
		//JSPへフォワード７
		req.getRequestDispatcher("test_list.jsp").forward(req, res);

}
	}
