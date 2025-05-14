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

			<form action = "test_list_subject.action"method="get">
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


					<div class="col-4">
						<label class="form-label" for="student-f1-select">科目</label>
						<select class="form-select" id="student-f1-select" name="f3">
							<option value="0">--------</option>
							<c:forEach var="f3" items="${f3}">
								<option value="${f3} }"<c:if test="${year==f3 }">selected</c:if>>${f3 }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>


					</div>

					</form>
					<form action = "TestListStudentExecute.action"method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">

					<div class="col-4">
						<label class="form-label" for="subject-name-input">学生番号</label>
						<input class="form-control" autocomplete="off"
							id="id-input" maxlength="10" name="name" placeholder="学生番号を入力してください"
							style="ime-mode: disabled" type="text" value="${name}"required />
					</div>


					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">絞込み</button>
					</div>
					</div>
					<div class="mt-2 text-warning">${errors.get("f1") }</div>

			</form>
			<p>科目情報を選択または、学生情報を入力して検索ボタンをクリックしてください</p>


		</section>
	</c:param>
</c:import>




