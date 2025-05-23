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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績一覧(学生)</h2>

			<form action="TestListSubjectExecute.action" method="post">
				<div class="border mx-3 mb-3 py-2 px-3 rounded" id="filter-area">
					<%-- 科目情報セクション --%>
					<div class="row mb-3">
						<%-- 新しいrowでラップ --%>
						<div class="col-md-2 pt-4">
							<%-- ラベル用の列、pt-1で少し上に調整 --%>
							<h4 class="h5">科目情報</h4>
						</div>
						<div class="col-md-10">
							<%-- 入力フォーム群用の列 --%>
							<div id="filter-subject-area">
								<%-- 元のIDはこちらに --%>
								<div class="row align-items-center">
									<div class="col-md-3">
										<label class="form-label" for="student-f1-select">入学年度</label>
										<select class="form-select" id="student-f1-select" name="f1">
											<option value="0">--------</option>
											<c:forEach var="year" items="${ent_year_set}">
												<option value="${year}"
													<c:if test="${year == f1}">selected</c:if>>${year}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-3">
										<label class="form-label" for="student-f2-select">クラス</label>
										<select class="form-select" id="student-f2-select" name="f2">
											<option value="0">--------</option>
											<c:forEach var="num" items="${class_num_set}">
												<option value="${num}"
													<c:if test="${num == f2}">selected</c:if>>${num}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-3">
										<label class="form-label" for="student-f3-select">科目</label> <select
											class="form-select" id="student-f3-select" name="f3">
											<option value="0">--------</option>
											<c:forEach var="subject" items="${subjectName}">
												<option value="${subject.cd}"
													<c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-2 d-flex align-items-end">
										<button type="submit" class="btn btn-secondary"
											id="filter-subject-button">検索</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
			<form action="TestListStudentExecute.action" method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded"
					id="filter">
					<div class="col-md-2 pt-1">
						<%-- ラベル用の列、pt-1で少し上に調整 --%>
						<h4 class="h5">学生情報</h4>
					</div>

					<div class="col-4">
						<label class="form-label" for="subject-name-input">学生番号</label> <input
							class="form-control" autocomplete="off" id="id-input"
							maxlength="10" name="name" placeholder="学生番号を入力してください"
							style="ime-mode: disabled" type="text" value="${name}" required />
					</div>


					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>
				</div>
				<div class="mt-2 text-warning">${errors.get("f1") }</div>

			</form>
			<%--<p>科目情報を選択または、学生情報を入力して検索ボタンをクリックしてください</p>--%>
			<c:choose>
				<c:when test="${list.size()>0}">
					<div>氏名 : ${student.name} (${name})</div>
					<table class="table table-hover">
						<tr>
							<th>名前</th>
							<th>科目コード</th>
							<th>点数</th>
							<th>回数</th>

							<th></th>
							<th></th>
						</tr>
						<c:forEach var="list" items="${list }">
							<tr>
								<td>${list.subjectName}</td>
								<td>${list.subjectCd }</td>
								<td>${list.point}</td>
								<td>${list.num}</td>


							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<div>学生情報が存在しませんでした。</div>
				</c:otherwise>
			</c:choose>


		</section>
	</c:param>
</c:import>