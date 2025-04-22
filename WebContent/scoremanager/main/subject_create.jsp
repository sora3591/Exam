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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>


			<form action ="SubjectCreateExecute.action"method="get">


				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-4">
						<label class="form-label" for="student-no-input">科目コード</label>
						<input class="form-control" autocomplete="off"
							id="no-input" maxlength="20" name="cd" placeholder="科目コードを入力してください"
							style="ime-mode: disabled;" type="text" value="${cd}" required />
							<div class="mt-2 text-warning">${errors.get("f2") }</div>
					</div>
				</div>

				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-4">
						<label class="form-label" for="student-name-input">科目名</label>
						<input class="form-control" autocomplete="off"
							id="id-input" maxlength="10" name="name" placeholder="科目名を入力してください"
							style="ime-mode: disabled" type="text" value="${name}"required >
					</div>
				</div>



					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">登録して終了</button>
					</div>
					</form>

					<a href="SubjectList.action">戻る</a>
					<div class="mt-2 text-warning">${errors.get("ent_year") }</div>



		</section>
	</c:param>
</c:import>
