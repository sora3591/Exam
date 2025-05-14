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
				<fieldset>
					<legend>科目情報</legend>
					<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
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
					</div>
				</fieldset>
				<fieldset>
					<legend>学生情報</legend>
					<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
						<div class="col-md-3">
							<label class="form-label" for="student-f4-input">学生番号</label>
							<input class="form-control" type="text" id="student-f4-input" name="f4" value="${f4}" placeholder="学生番号を入力してください" maxlength="10">
						</div>
					</div>
				</fieldset>
				<div class="col-12 text-center mt-3">
					<button type="submit" class="btn btn-secondary" id="filter-button">検索</button>
				</div>
			</form>

			<c:if test="${searched}">
				<c:choose>
					<c:when test="${not empty testList}">
						<c:if test="${not empty subjectName}">
							<p>科目： ${subjectName}</p>
						</c:if>
						<h3 class="h4 mb-3">検索結果</h3>
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
			<c:if test="${not searched}">
				<p>入学年度、クラス、科目、学生番号のいずれかを入力または選択して検索してください。</p>
			</c:if>

		</section>
	</c:param>
</c:import>
