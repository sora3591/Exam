
package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action{
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");


		String entYearStr="";//入力された入学年度
		String classNum="";//入力されたクラス番号
		LocalDate todaysDate=LocalDate.now();//LocalDateインスタンスを取得
		int year=todaysDate.getYear();//現在の年を取得
		ClassNumDao cNumDao=new ClassNumDao();//クラス番号Daoを初期化
        SubjectDao subjectDao = new SubjectDao();
        int entYear=0;//入学年度
        int times = 0; 		//回数
        TestDao testDao = new TestDao();	//成績情報
        StudentDao studentDao = new StudentDao();








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






		req.setAttribute("subjectname",subjectNames);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set",list);



		//リクエストに入学年度をセット
		req.setAttribute("f1",entYear);
		//リクエストにクラス番号をセット
		req.setAttribute("f2",classNum);

		req.setAttribute("f3", subjectNames);

		if(req.getParameter("f1")!=null){
		List<Student>students=null;

		boolean isAttend= true;

		int entYear2 = Integer.parseInt(req.getParameter("f1"));

		String classNum2=req.getParameter("f2");



		students=studentDao.filter(teacher.getSchool(), entYear2, classNum2, isAttend);

		req.setAttribute("student",students);
		}


		req.getRequestDispatcher("test_regist.jsp").forward(req, res);





}
}
