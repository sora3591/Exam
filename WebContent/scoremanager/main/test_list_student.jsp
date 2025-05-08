<<<<<<< HEAD
<%-- メニューJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生管理</h2>
			<div class=" my-2 text-end px-4">
				<a href="StudentCreate.action">新規登録</a>
			</div>
			<form method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-4">
						<label class="form-label" for="student-f1-select">入学年度</label>
						<select class="form-select" id="student-f1-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set}">
								<%--現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
								<option value="${year }"<c:if test="${year==f1 }">selected</c:if>>${year }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4">
						<label class="form-label" for="student-f2-select">クラス</label>
						<select class="form-select" id="student-f2-select" name="f2">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set}">
								<%--現在のyearと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${num }"<c:if test="${num==f2 }">selected</c:if>>${num }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2 form-check text-center">
						<label class="form-check-label" for="student-f3-check">在学中
						<%--パラメーターf3が存在している場合checkedを追記 --%>
						<input class="form-check-input" type="checkbox"
						id="student-f3-check" name="f3" value="t"
						<c:if test="${!empty f3 }">checked</c:if>>
						</label>
					</div>
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">絞込み</button>
					</div>
					<div class="mt-2 text-warning">${errors.get("f1") }</div>
				</div>
			</form>
			<c:choose>
				<c:when test="${students.size()>0}">
					<div>検索結果 :${students.size() }件</div>
					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>クラス</th>
							<th class="text-center">在学中</th>
							<th></th>
							<th></th>
						</tr>
						<c:forEach var="student" items="${students }">
							<tr>
								<td>${student.entYear }</td>
								<td>${student.no }</td>
								<td>${student.name }</td>
								<td>${student.classNum }</td>
								<td class="text-center">
									<%--在学フラグがたっている場合「〇」それ以外は「☓」を表示 --%>
									<c:choose>
										<c:when test="${student.isAttend() }">
											〇
										</c:when>
										<c:otherwise>
											☓
										</c:otherwise>
									</c:choose>
								</td>
								<td><a href="StudentUpdate.action?no=${student.no }">変更</a></td>


							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<div>成績情報が存在しませんでした。</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>




=======
<%-- 学生別テスト一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム - 学生別テスト一覧
	</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生別テスト一覧</h2>
			<form method="post" action="TestListStudentExecute.action">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-8">
						<label class="form-label" for="student-select">学生</label>
						<select class="form-select" id="student-select" name="studentNo">
							<option value="">--------</option>
							<c:forEach var="student" items="${studentList}">
								<option value="${student.no}" <c:if test="${student.no==selectedStudentNo}">selected</c:if>>${student.name} (${student.no})</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4 text-center">
						<button type="submit" class="btn btn-primary" id="search-button">検索</button>
					</div>
					<div class="mt-2 text-warning">
						${errors.get("studentNo") }
					</div>
				</div>
			</form>

			<c:choose>
				<c:when test="${testListStudents != null && testListStudents.size() > 0}">
					<div class="mx-3 mb-2">検索結果: ${testListStudents.size()} 件</div>
					<table class="table table-hover mx-3">
						<thead>
							<tr>
								<th>科目コード</th>
								<th>科目名</th>
								<th>テスト回数</th>
								<th>得点</th>
								<th>合否判定</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="test" items="${testListStudents}">
								<tr>
									<td>${test.subjectCd}</td>
									<td>${test.subjectName}</td>
									<td>${test.num}</td>
									<td>${test.point}</td>
									<td>
										<c:choose>
											<c:when test="${test.point >= 60}">
												<span class="text-success">合格</span>
											</c:when>
											<c:otherwise>
												<span class="text-danger">不合格</span>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:when test="${testListStudents != null && testListStudents.size() == 0}">
					<div class="alert alert-info mx-3">検索条件に該当するテストデータは存在しませんでした。</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-secondary mx-3">学生を選択して検索ボタンをクリックしてください。</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>
>>>>>>> branch 'master' of https://github.com/sora3591/Exam.git
