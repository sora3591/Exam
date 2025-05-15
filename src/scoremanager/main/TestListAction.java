package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");
        String studentNo = req.getParameter("f4"); // 学生番号は任意入力

        int entYear = 0;
        // Map<String, String> errors = new HashMap<>(); // 既存のエラー処理はそのまま活用しても良いですが、今回は直接error_messageをセット

        // 入学年度のパース (既存のロジックを流用)
        if (entYearStr != null && !entYearStr.equals("0")) {
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (NumberFormatException e) {
                // errors.put("f1", "入学年度は数値を入力してください"); // 既存のエラー処理
                // ここでは、必須項目エラーとは別なので、このエラー処理は残しても良い
            }
        }

        // 必須項目が選択されているかどうかのチェック
        boolean requiredFieldsSelected = true;
        if (entYearStr == null || entYearStr.equals("0") ||
            classNum == null || classNum.equals("0") ||
            subjectCd == null || subjectCd.equals("0") || subjectCd.isEmpty()) { // 科目コードは空も未選択とみなす
            requiredFieldsSelected = false;
        }

        LocalDate todaysDate = LocalDate.now();
        int currentAcademicYear = todaysDate.getYear(); // 現在の年度を取得
        ClassNumDao cNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        TestListStudentDao testDao = new TestListStudentDao();
        List<TestListStudent> testList = null;

        // プルダウン用のリスト取得 (変更なし)
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = currentAcademicYear - 10; i < currentAcademicYear + 1; i++) {
            entYearSet.add(i);
        }
        List<String> classNumList = cNumDao.filter(teacher.getSchool());
        List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

        req.setAttribute("searched", false); // 初期値はfalse

        if (requiredFieldsSelected) {
            req.setAttribute("searched", true); // 必須項目が選択されていれば検索実行の意思あり

            // 科目名の設定 (既存ロジックを流用、ただし subjectCd が "0" や空でないことを確認)
            if (subjectCd != null && !subjectCd.isEmpty() && !subjectCd.equals("0")) {
                for (Subject sub : subjectList) {
                    if (sub.getCd().equals(subjectCd)) {
                        req.setAttribute("subjectName", sub.getName());
                        break;
                    }
                }
            }

            // DAO呼び出し (学生番号は任意なので、空なら空として渡す)
            String studentNoForFilter = (studentNo == null) ? "" : studentNo;
            try {
                testList = testDao.filter(entYear, classNum, subjectCd, studentNoForFilter, teacher.getSchool());
            } catch (Exception e) {
                // req.setAttribute("error_message", "検索中にエラーが発生しました。"); // 必要に応じて
                e.printStackTrace(); // サーバーログには残す
            }
        } else {
            // 必須項目が未選択の場合
            // いずれかの検索ボタンが押されたが、必須項目が足りない場合のみエラーメッセージを表示
            // (初期表示時や、何も入力せずに遷移してきた場合はエラーにしない。
            // ただし、現状のJSPでは何かしらのボタンを押さないとこのActionには来ない想定)
            if (req.getParameter("f1") != null || req.getParameter("f2") != null || req.getParameter("f3") != null || req.getParameter("f4") != null) {
                 // ↑この条件は、何らかの検索操作が行われたことを示す（より厳密にはボタンのname属性などで判断すべきだが、ここでは簡易的に）
                 req.setAttribute("error_message", "入学年度、クラス、科目を選択してください。");
            }
            req.setAttribute("searched", false); // 検索は実行されなかった
        }

        // req.setAttribute("errors", errors); // 既存のエラーマップを引き続き使う場合
        req.setAttribute("testList", testList);
        req.setAttribute("f1", entYearStr != null ? entYearStr : "0");
        req.setAttribute("f2", classNum != null && !classNum.equals("0") ? classNum : "0");
        req.setAttribute("f3", subjectCd != null && !subjectCd.equals("0") ? subjectCd : "0"); // "0" も未選択として扱う
        req.setAttribute("f4", studentNo != null ? studentNo : "");

        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumList);
        req.setAttribute("subject_list", subjectList);

        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}