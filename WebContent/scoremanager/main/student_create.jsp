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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>


			<form action ="StudentCreateExecute.action"method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-4">
						<label class="form-label" for="student-ent_year-select">入学年度</label>
						<select class="form-select" id="student-ent_year-select" name="ent_year">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set}">
								<%--現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
								<option value="${year }"<c:if test="${year==ent_year }">selected</c:if>>${year }</option>
							</c:forEach>
						</select>
						<div class="mt-2 text-warning">${errors.get("f1") }</div>
					</div>

				</div>

				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-4">
						<label class="form-label" for="student-no-input">学生番号</label>
						<input class="form-control" autocomplete="off"
							id="no-input" maxlength="20" name="no" placeholder="学生番号を入力してください"
							style="ime-mode: disabled;" type="text" value="${no}" required />
							<div class="mt-2 text-warning">${errors.get("f2") }</div>
					</div>
				</div>

				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-4">
						<label class="form-label" for="student-name-input">氏名</label>
						<input class="form-control" autocomplete="off"
							id="id-input" maxlength="10" name="name" placeholder="氏名を入力してください"
							style="ime-mode: disabled" type="text" value="${name}"required >
					</div>
				</div>


			<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
			<div class="col-4">
						<label class="form-label" for="student-class_num-select">クラス</label>
						<select class="form-select" id="student-class_num-select" name="class_num">

							<c:forEach var="num" items="${class_num_set}">
								<%--現在のyearと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${num }"<c:if test="${num==class_num }">selected</c:if>>${num }</option>
							</c:forEach>
						</select>
					</div>
					</div>
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">登録して終了</button>
					</div>
					</form>

					<a href="StudentList.action">戻る</a>
					<div class="mt-2 text-warning">${errors.get("ent_year") }</div>



		</section>
	</c:param>
</c:import>
