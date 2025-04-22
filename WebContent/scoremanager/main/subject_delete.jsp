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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>
			<c:if test="${not empty errors}">
				<div class="alert alert-danger">
						<c:forEach var="error" items="${errors}">
							<li>${error}</li>
						</c:forEach>
				</div>

			</c:if>
			<form action ="SubjectDeleteExecute.action"method="get">

					<div class="col-4">
						<input hidden="form-control" autocomplete="off"
							id="no-input" maxlength="20" name="subject_cd" placeholder=""
							style="ime-mode: disabled;" type="text" value="${subject_name}(${subject_cd})を削除してもよろしいですか" />
					</div>


					<div class="mt-4">
						<input class="w-39 btn btn-lg btn-primary" type="submit" name="login" value="削除"/>
					</div>

					<div class="col-2 text-center"><a href="SubjectList.action">戻る</a></div>

			</form>
		</section>
	</c:param>
</c:import>


