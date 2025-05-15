<%-- メニューJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		成績一覧（科目）
	</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績一覧（科目）</h2>

			<form action="TestList.action" method="post">
				<div class="border mx-3 mb-3 py-2 px-3 rounded" id="filter-area">
					<%-- 科目情報セクション --%>
					<div class="row mb-3"> <%-- 新しいrowでラップ --%>
						<div class="col-md-2 pt-1"> <%-- ラベル用の列、pt-1で少し上に調整 --%>
							<h4 class="h5">科目情報</h4>
						</div>
						<div class="col-md-10"> <%-- 入力フォーム群用の列 --%>
							<div id="filter-subject-area"> <%-- 元のIDはこちらに --%>
								<div class="row align-items-center">
									<div class="col-md-3">
										<label class="form-label" for="student-f1-select">入学年度</label>
										<select class="form-select" id="student-f1-select" name="f1">
											<option value="0">--------</option>
											<c:forEach var="year" items="${ent_year_set}">
												<option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-3">
										<label class="form-label" for="student-f2-select">クラス</label>
										<select class="form-select" id="student-f2-select" name="f2">
											<option value="0">--------</option>
											<c:forEach var="num" items="${class_num_set}">
												<option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-3">
										<label class="form-label" for="student-f3-select">科目</label>
										<select class="form-select" id="student-f3-select" name="f3">
											<option value="0">--------</option>
											<c:forEach var="subject" items="${subject_list}">
												<option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>${subject.name}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-2 d-flex align-items-end">
										<button type="submit" class="btn btn-secondary" id="filter-subject-button">検索</button>
									</div>
								</div>
							</div>
						</div>
					</div>
					<%-- 旧 <div id="filter-subject-area" class="mb-3"> の閉じタグは不要になる --%>
					<%-- 旧 <h3>科目情報</h3> は移動済み --%>
					<%-- 科目情報セクションの入力フォームを囲んでいた <div class="row align-items-center"> は、新しい構造の <div class="col-md-10"> の内側にあります --%>
					<%-- 元の line 50 にあった </div> は、新しい構造では col-md-10 の閉じタグに対応します --%>

					<hr class="mx-3">

					<%-- 学生情報セクション --%>
					<div class="row"> <%-- 新しいrowでラップ --%>
						<div class="col-md-2 pt-1"> <%-- ラベル用の列 --%>
							<h4 class="h5">学生情報</h4>
						</div>
						<div class="col-md-10"> <%-- 入力フォーム群用の列 --%>
							<div id="filter-student-area"> <%-- 元のIDはこちらに --%>
								<div class="row align-items-center">
									<div class="col-md-3">
										<label class="form-label" for="student-f4-input">学生番号</label>
										<input class="form-control" type="text" id="student-f4-input" name="f4" value="${f4}" placeholder="学生番号を入力してください" maxlength="10">
									</div>
									<div class="col-md-2 d-flex align-items-end">
										<button type="submit" class="btn btn-secondary" id="filter-student-no-button">検索</button>
									</div>
								</div>
							</div>
						</div>
					</div>
					<%-- 旧 <div id="filter-student-area"> の閉じタグは不要になる --%>
					<%-- 旧 <h3>学生情報</h3> は移動済み --%>
			</form>

			<c:if test="${searched}">
				<c:choose>
					<c:when test="${not empty testList}">
						<c:if test="${not empty subjectName}">
							<h3 class="h4 mb-3">検索結果： ${subjectName}</h3>
						</c:if>
						<c:if test="${empty subjectName}">
							<h3 class="h4 mb-3">検索結果</h3>
						</c:if>
						<table class="table table-hover">
							<thead>
								<tr>
									<th>入学年度</th>
									<th>クラス</th>
									<th>学生番号</th>
									<th>氏名</th>
									<!-- ★ ヘッダー追加 ★ -->
									<th>1回</th>
									<th>2回</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="student" items="${testList}">
									<tr>
										<td>${student.entYear}</td>
										<td>${student.classNum}</td>
										<td>${student.studentNo}</td>
										<td>${student.studentName}</td>
										<!-- ディスプレイスコア、nullなら-を表示 -->
										<td>
											<c:choose>
												<c:when test="${student.point1 != null}">${student.point1}</c:when>
												<c:otherwise>-</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
												<c:when test="${student.point2 != null}">${student.point2}</c:when>
												<c:otherwise>-</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:when>
					<c:otherwise>
						<p>学生情報が存在しませんでした</p>
					</c:otherwise>
				</c:choose>
			</c:if>

			<%-- メッセージ表示エリア --%>
			<c:choose>
				<c:when test="${not empty error_message}">
					<p class="text-warning">${error_message}</p> <%-- text-danger から text-warning へ --%>
				</c:when>
				<c:when test="${not searched}">
					<p class="text-info">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</p> <%-- テキスト変更、text-info クラス追加 --%>
				</c:when>
			</c:choose>

		</section>
	</c:param>
</c:import>
