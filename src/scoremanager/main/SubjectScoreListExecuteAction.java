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

public class SubjectScoreListExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            res.sendRedirect("../login.jsp");
            return;
        }

        // ドロップダウン用リストの準備
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        List<Integer> yearList = new ArrayList<>();
        for (int i = currentYear; i >= currentYear - 10; i--) {
            yearList.add(i);
        }
        req.setAttribute("yearList", yearList);

        // クラスリストを取得
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classList = classNumDao.filter(teacher.getSchool());
        req.setAttribute("classList", classList);

        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList = subjectDao.filter(teacher.getSchool());
        req.setAttribute("subjectList", subjectList);

        // パラメータの取得
        String yearStr = req.getParameter("year");
        String classNum = req.getParameter("classNum");
        String subjectIdStr = req.getParameter("subjectId");

        Map<String, String> errors = new HashMap<>();
        List<TestListSubject> scoreList = null;

        // 検索条件が指定されているか確認 (POSTリクエストなど)
        boolean searchExecuted = yearStr != null && !yearStr.equals("0") &&
                                classNum != null && !classNum.equals("0") &&
                                subjectIdStr != null && !subjectIdStr.equals("0");

        if (searchExecuted) {
            int year = 0;
            String subjectCd = null;

            // 年度のバリデーション (Integerに変換できるか)
            try {
                year = Integer.parseInt(yearStr);
            } catch (NumberFormatException e) {
                errors.put("filter", "年度を正しく選択してください。");
            }

            // クラス番号のバリデーション (空でないか - リストから選択されるはず)
            if (classNum == null || classNum.isEmpty() || classNum.equals("0")) {
                 errors.put("filter", "クラスを選択してください。");
            }

            // 科目IDのバリデーション (空でないか)
            if (subjectIdStr == null || subjectIdStr.isEmpty() || subjectIdStr.equals("0")) {
                errors.put("filter", "科目を選択してください。");
            } else {
                subjectCd = subjectIdStr;
            }


            // エラーがない場合は成績データを取得
            if (errors.isEmpty()) {
                TestListSubjectDao testListSubjectDao = new TestListSubjectDao();
                Subject selectedSubject = null;
                for (Subject subject : subjectList) {
                    if (subject.getCd().equals(subjectCd)) {
                        selectedSubject = subject;
                        break;
                    }
                }

                if (selectedSubject != null) {
                    // DAO呼び出しに classNum を追加
                    scoreList = testListSubjectDao.filter(year, classNum, selectedSubject, teacher.getSchool());

                    // ランキングロジックはDAOの戻り値に合わせて要確認
                    if (scoreList != null && !scoreList.isEmpty()) {
                        // 既存のランキングロジック (TestListSubject.points の最大値基準)
                        scoreList.sort((s1, s2) -> {
                            int p1 = s1.getPoints() != null && !s1.getPoints().isEmpty() ? s1.getPoints().values().stream().max(Integer::compareTo).orElse(Integer.MIN_VALUE) : Integer.MIN_VALUE;
                            int p2 = s2.getPoints() != null && !s2.getPoints().isEmpty() ? s2.getPoints().values().stream().max(Integer::compareTo).orElse(Integer.MIN_VALUE) : Integer.MIN_VALUE;
                            return Integer.compare(p2, p1); // 降順ソート
                        });

                        int rank = 1;
                        int lastPoint = -1;
                        int sameRankCount = 1;
                        for (int i = 0; i < scoreList.size(); i++) {
                            TestListSubject score = scoreList.get(i);
                            int point = score.getPoints() != null && !score.getPoints().isEmpty() ? score.getPoints().values().stream().max(Integer::compareTo).orElse(Integer.MIN_VALUE) : Integer.MIN_VALUE;

                            if (i > 0 && point == lastPoint) {
                                req.setAttribute("rank_" + score.getStudentNo(), rank);
                                req.setAttribute("max_point_" + score.getStudentNo(), point);
                                sameRankCount++;
                            } else {
                                rank += (i > 0 ? sameRankCount : 0);
                                req.setAttribute("rank_" + score.getStudentNo(), rank);
                                req.setAttribute("max_point_" + score.getStudentNo(), point);
                                lastPoint = point;
                                sameRankCount = 1;
                            }
                        }
                    }
                    req.setAttribute("scoreList", scoreList);
                } else if (errors.isEmpty()) {
                     errors.put("filter", "選択された科目が無効です。");
                }

                // 選択された値をリクエスト属性に設定してJSPで再表示
                req.setAttribute("selectedYear", year);
                req.setAttribute("selectedClassNum", classNum);
                req.setAttribute("selectedSubjectId", subjectCd);

            }
            // エラーがある場合でも、選択された値は保持して表示する
            else {
                 try { req.setAttribute("selectedYear", Integer.parseInt(yearStr)); } catch (Exception e) {/* ignore */}
                 req.setAttribute("selectedClassNum", classNum);
                 req.setAttribute("selectedSubjectId", subjectIdStr);
            }

        } else if (req.getMethod().equalsIgnoreCase("POST")) {
             // POSTリクエストだが必須項目が選択されていない場合
             if (yearStr == null || yearStr.equals("0") ||
                 classNum == null || classNum.equals("0") ||
                 subjectIdStr == null || subjectIdStr.equals("0")) {
                 errors.put("filter", "年度、クラス、科目をすべて選択してください。");
                 try { req.setAttribute("selectedYear", Integer.parseInt(yearStr)); } catch (Exception e) {/* ignore */}
                 req.setAttribute("selectedClassNum", classNum);
                 req.setAttribute("selectedSubjectId", subjectIdStr);
             }
        }
        // GETリクエストや初期表示の場合はエラーなし、結果なし

        req.setAttribute("errors", errors);
        req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
    }
}
