package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

	 @Override
	    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	        // セッションからユーザー情報を取得
	        HttpSession session = req.getSession();
	        Teacher teacher = (Teacher) session.getAttribute("user");




	        String f3 = req.getParameter("f3");


			String entYearStr = "";// 入力された入学年度
			String classNum1 = "";// 入力されたクラス番号
			int entYear = 0;// 入学年度
			LocalDate todaysDate = LocalDate.now();// LocalDateインスタンスを取得
			int year1 = todaysDate.getYear();// 現在の年を取得
			ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
			SubjectDao subjectDao = new SubjectDao();
			int times = 0; // 回数

			Subject subjectname=subjectDao.get(f3, teacher.getSchool());
			System.out.println(subjectname.getName()+"------------------");



			List<Integer> entYearSet = new ArrayList<>();
			// １０年前から１年後まで年をリストに追加
			for (int i = year1 - 10; i < year1 + 1; i++) {
				entYearSet.add(i);
			}
			System.out.println(cNumDao.filter(teacher.getSchool()));

			// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
			List<String> list = cNumDao.filter(teacher.getSchool());

			List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

			// 科目名だけを取り出す（必要なら）
			List<Subject> subjectNames = subjectDao.filter(teacher.getSchool());

//			for (Subject subject : subjectList) {
//				subjectNames.add(subject.getName());
//			}

			req.setAttribute("subjectName", subjectNames);

			// リクエストに入学年度をセット
			req.setAttribute("f1", entYear);
			// リクエストにクラス番号をセット
			req.setAttribute("f2", classNum1);

			req.setAttribute("ent_year_set", entYearSet);
			req.setAttribute("class_num_set", list);
			req.setAttribute("subjectIdStr", subjectname.getName());















	        if (teacher == null) {
	            // ログインしていない場合はログイン画面にリダイレクト
	            res.sendRedirect("../login.jsp");
	            return;
	        }

	        // 年度のリストを作成（現在の年から10年前まで）
	        LocalDate today = LocalDate.now();
	        int currentYear = today.getYear();
	        List<Integer> yearList = new ArrayList<>();
	        for (int i = currentYear; i >= currentYear - 10; i--) {
	            yearList.add(i);
	        }
	        req.setAttribute("yearList", yearList);

	        // クラス一覧を取得
	        ClassNumDao classNumDao = new ClassNumDao();
	        List<String> classList = classNumDao.filter(teacher.getSchool());
	        req.setAttribute("classList", classList);

	        // 科目リストを取得
//	        SubjectDao subjectDao = new SubjectDao();
//	        List<Subject> subjectList = subjectDao.filter(teacher.getSchool());
//	        req.setAttribute("subjectList", subjectList);

	        // パラメータの取得とバリデーション
	        String yearStr = req.getParameter("f1");
	        String classNumStr = req.getParameter("f2");
	        String subjectIdStr = req.getParameter("f3");
	        System.out.println(yearStr);
	        System.out.println(classNumStr);
	        System.out.println(subjectIdStr);
//	        req.setAttribute("subjectIdStr", subjectIdStr);



	        // エラーメッセージ用のマップ
	        Map<String, String> errors = new HashMap<>();

	        // 検索条件が指定されている場合
	        if (yearStr != null && classNumStr != null && subjectIdStr != null) {
	            int year = 0;
	            String classNum = null;
	            String subjectCd = null;

	            // 年度のバリデーション
	            try {
	                year = Integer.parseInt(yearStr);
	                if (year <= 0) {
	                    errors.put("year", "年度を選択してください。");
	                }
	            } catch (NumberFormatException e) {
	                errors.put("year", "年度を選択してください。");
	            }

	            // クラス番号のバリデーション
	            if (classNumStr == null || classNumStr.isEmpty()) {
	                errors.put("classNum", "クラスを選択してください。");
	            } else {
	                classNum = classNumStr;
	            }

	            // 科目IDのバリデーション
	            if (subjectIdStr == null || subjectIdStr.equals("0") || subjectIdStr.isEmpty()) {
	                errors.put("subjectId", "科目を選択してください。");
	            } else {
	                subjectCd = subjectIdStr;
	            }

	            // エラーがない場合は成績データを取得
	            if (errors.isEmpty()) {
	                TestListSubjectDao testListSubjectDao = new TestListSubjectDao();

	                // 選択された科目を取得
//	                Subject selectedSubject = null;
//	                for (Subject subject : subjectList) {
//	                    if (subject.getCd().equals(subjectCd)) {
//	                        selectedSubject = subject;
//	                        break;
//	                    }
//	                }

	                if (true) {
	                    // 成績データを取得（年度・クラス・科目でfilter）
	                	Subject subject=new Subject();
	                	subject.setCd(subjectCd);
	                    List<TestListSubject> scoreList = testListSubjectDao.filter(year, classNum, subject, teacher.getSchool());


	                    // 点数の最大値でソート
	                    if (scoreList != null && !scoreList.isEmpty()) {
	                        scoreList.sort((s1, s2) -> {
	                            int p1 = s1.getPoints() != null && !s1.getPoints().isEmpty() ? s1.getPoints().values().stream().max(Integer::compareTo).orElse(0) : 0;
	                            int p2 = s2.getPoints() != null && !s2.getPoints().isEmpty() ? s2.getPoints().values().stream().max(Integer::compareTo).orElse(0) : 0;
	                            return Integer.compare(p2, p1);
	                        });

	                        // 順位計算
	                        int rank = 1;
	                        int lastPoint = -1;
	                        int sameRankCount = 0;
	                        for (TestListSubject score : scoreList) {
	                            int point = score.getPoints() != null && !score.getPoints().isEmpty() ? score.getPoints().values().stream().max(Integer::compareTo).orElse(0) : 0;
	                            if (lastPoint == point) {
	                                sameRankCount++;
	                            } else {
	                                rank += sameRankCount;
	                                sameRankCount = 0;
	                                lastPoint = point;
	                            }
	                            req.setAttribute("rank_" + score.getStudentNo(), rank);
	                        }
	                    }

	                    req.setAttribute("scoreList", scoreList);

	                }

	                req.setAttribute("selectedYear", year);
	                req.setAttribute("selectedClassNum", classNum);
	                req.setAttribute("selectedSubjectId", subjectCd);
	            }
	        }

	        req.setAttribute("errors", errors);
	        req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
	    }

}
