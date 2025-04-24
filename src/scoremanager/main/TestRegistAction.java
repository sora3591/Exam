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

public class TestRegistAction extends Action{
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


		List<Integer>entYearSet = new ArrayList<>();
		//１０年前から１年後まで年をリストに追加
		for(int i=year-10; i<year+1;i++){
			entYearSet.add(i);
		}

		//ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list=cNumDao.filter(teacher.getSchool());


        String subjectCdStr = "";
        String subjectName = "";
        int subjectCd = 0;
        List<Subject> subjects = new ArrayList<>();


        // 全件取得
        List<Subject> allSubjects = subjectDao.filter(teacher.getSchool());

        // Java側でフィルター処理
        for (Subject subject : allSubjects) {
            boolean matches = true;

            // 科目コードによるフィルター（部分一致）
            if (subjectCd != 0) {
                try {
                    int subjectCdFromDb = Integer.parseInt(subject.getCd());
                    if (subjectCdFromDb != subjectCd) {
                        matches = false;
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
            }

            // 科目名によるフィルター（部分一致）
            if (subjectName != null && !subjectName.isEmpty() && !subjectName.equals("0")) {
                if (!subject.getName().contains(subjectName)) {
                    matches = false;
                }
            }

            if (matches) {
                subjects.add(subject);
            }
        }





		//リクエストに入学年度をセット
		req.setAttribute("f1",entYear);
		//リクエストにクラス番号をセット
		req.setAttribute("f2",classNum);

		req.setAttribute("f3", subjects);


		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set",list);
		req.setAttribute("subject_name_set", subjects);


		req.getRequestDispatcher("test_regist.jsp").forward(req, res);

		System.out.println(allSubjects);



}
}