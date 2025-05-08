<%-- メニューJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
			<div class="alert alert-success text-center fs-5 py-4 rounded shadow">
				登録が完了しました。
			</div>

			<a href="TestRegist.action">戻る</a>
			<a href="TestList.action">成績参照</a>
		</section>
	</c:param>
</c:import>