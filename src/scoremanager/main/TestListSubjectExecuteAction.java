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
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList = subjectDao.filter(teacher.getSchool());
        req.setAttribute("subjectList", subjectList);

        // パラメータの取得とバリデーション
        String yearStr = req.getParameter("year");
        String classNumStr = req.getParameter("classNum");
        String subjectIdStr = req.getParameter("subjectId");

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
                Subject selectedSubject = null;
                for (Subject subject : subjectList) {
                    if (subject.getCd().equals(subjectCd)) {
                        selectedSubject = subject;
                        break;
                    }
                }

                if (selectedSubject != null) {
                    // 成績データを取得（年度・クラス・科目でfilter）
                    List<TestListSubject> scoreList = testListSubjectDao.filter(year, classNum, selectedSubject, teacher.getSchool());

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
