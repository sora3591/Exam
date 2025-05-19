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
			<div class="alert alert-success text-center fs-5 py-4 rounded shadow">
     			  変更が完了しました。
			</div>


			<a href="ClassList.action">クラス一覧</a>
			<c:if test="${not empty errors}">
				<div class="alert alert-danger">
					<ul>
						<c:forEach var="error" items="${errors}">
							<li>${error}</li>
						</c:forEach>
					</ul>
				</div>

			</c:if>


					<table class="table table-hover">
						<tr>
							<th>科目コード</th>
							<th>科目名</th>

							<th></th>
							<th></th>
						</tr>
						<c:forEach var="students" items="${students }">
							<tr>
								<td>${students.no }</td>
								<td>${students.name }</td>

								<td><a href="SubjectUpdate.action?no=${subject.cd }">変更</a></td>
								<td><a href="SubjectDelete.action?no=${subject.cd }">削除</a></td>
							</tr>
						</c:forEach>
					</table>


			</section>
			</c:param>
			</c:import>
