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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">クラス情報変更</h2>
			<c:if test="${not empty errors}">
				<div class="alert alert-danger">
					<ul>
						<c:forEach var="error" items="${errors}">
							<li>${error}</li>
						</c:forEach>
					</ul>
				</div>
			</c:if>

			<form action ="ClassUpdateExecute.action"method="get">

				<input type="hidden" name="no" value="${no}">

				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-4">
						<label class="form-label" for="subject-no-input">元のクラス番号</label>
						<input class="form-control" autocomplete="off"
							id="no-input" maxlength="20" name="cd" placeholder=""
							style="ime-mode: disabled;" type="text" value="${no}" readonly />
					</div>
				</div>

				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-4">
						<label class="form-label" for="student-no-input">クラスコード</label>
						<input class="form-control" autocomplete="off"
							id="no-input" maxlength="3" name="classnum" placeholder="クラスコードを入力してください"
							style="ime-mode: disabled;" type="text" value="${classnum2}" required />

					</div>
					<div class="mt-2 text-warning">${errors.get("f1") }</div>
					</div>



					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">変更</button>
					</div>

					<div class="col-2 text-center"><a href="ClassList.action">戻る</a></div>
					</form>

					<div class="mt-2 text-warning">${errors.get("ent_year") }</div>

		</section>
	</c:param>
</c:import>
