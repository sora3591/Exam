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
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");


		String entYearStr="";//入力された入学年度
		String classNum="";//入力されたクラス番号
		int entYear=0;//入学年度
		LocalDate todaysDate=LocalDate.now();//LocalDateインスタンスを取得
		int year=todaysDate.getYear();//現在の年を取得
		ClassNumDao cNumDao=new ClassNumDao();//クラス番号Daoを初期化
        SubjectDao subjectDao = new SubjectDao();
        int times = 0; 		//回数



		List<Integer>entYearSet = new ArrayList<>();
		//１０年前から１年後まで年をリストに追加
		for(int i=year-10; i<year+1;i++){
			entYearSet.add(i);
		}

		//ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list=cNumDao.filter(teacher.getSchool());


		List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

		// 科目名だけを取り出す（必要なら）
		List<String> subjectNames = new ArrayList<>();

		for (Subject subject : subjectList) {
		    subjectNames.add(subject.getName());
		}




		req.setAttribute("f3",subjectNames);



		//リクエストに入学年度をセット
		req.setAttribute("f1",entYear);
		//リクエストにクラス番号をセット
		req.setAttribute("f2",classNum);




		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set",list);





		req.getRequestDispatcher("test_list.jsp").forward(req, res);





}
}