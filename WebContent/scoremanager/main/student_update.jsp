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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報更新</h2>
			<c:if test="${not empty errors}">
				<div class="alert alert-danger">
					<ul>
						<c:forEach var="error" items="${errors}">
							<li>${error}</li>
						</c:forEach>
					</ul>
				</div>
			</c:if>

			<form action ="StudentUpdateExecute.action"method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-4">
						<label class="form-label" for="student-ent_year-select">入学年度</label>
						<input class="form-control" autocomplete="off"
							id="no-input" maxlength="20" name="ent_year" placeholder=""
							style="ime-mode: disabled;" type="text" value="${student.entYear}" readonly />
					</div>
				</div>

				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-4">
						<label class="form-label" for="student-no-input">学生番号</label>
						<input class="form-control" autocomplete="off"
							id="no-input" maxlength="20" name="no" placeholder=""
							style="ime-mode: disabled;" type="text" value="${student.no}" readonly />
					</div>
				</div>

				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-4">
						<label class="form-label" for="student-name-input">氏名</label>
						<input class="form-control" autocomplete="off"
							id="id-input" maxlength="10" name="name" placeholder="氏名を入力してください"
							style="ime-mode: disabled" type="text" value="${name}"required />
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
					<div class="col-2 form-check text-center">
						<label class="form-check-label" for="student-f3-check">在学中
						<%--パラメーターf3が存在している場合checkedを追記 --%>
						<input class="form-check-input" type="checkbox"
						id="student-f3-check" name="f3" value="t"
						<c:if test="${!empty f3 }">checked</c:if>>
						</label>
					</div>
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">変更</button>
					</div>
					<div class="col-2 text-center"><a href="StudentList.action">戻る</a></div>
					</form>


					<div class="mt-2 text-warning">${errors.get("ent_year") }</div>

		</section>
	</c:param>
</c:import>
