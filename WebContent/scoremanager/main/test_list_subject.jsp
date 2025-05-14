<%-- 科目別成績一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム - 科目別成績一覧
	</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目別成績一覧</h2>
			<form method="post" action="SubjectScoreListExecute.action">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-4">
						<label class="form-label" for="year-select">年度</label>
						<select class="form-select" id="year-select" name="year">
							<option value="0">--------</option>
							<c:forEach var="year" items="${yearList}">
								<option value="${year}" <c:if test="${year==selectedYear}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4">
						<label class="form-label" for="subject-select">科目</label>
						<select class="form-select" id="subject-select" name="subjectId">
							<option value="0">--------</option>
							<c:forEach var="subject" items="${subjectList}">
								<option value="${subject.cd}" <c:if test="${subject.cd==selectedSubjectId}">selected</c:if>>${subject.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4">
						<label class="form-label" for="class-num-select">クラス</label>
						<select class="form-select" id="class-num-select" name="classNum">
							<option value="0">--------</option>
							<c:forEach var="classNum" items="${classList}">
								<option value="${classNum}" <c:if test="${classNum==selectedClassNum}">selected</c:if>>${classNum}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-1 text-center">
						<button type="submit" class="btn btn-primary" id="search-button">検索</button>
					</div>
					<div class="mt-2 text-warning">
						${errors.get("filter") }
					</div>
				</div>
			</form>

			<c:choose>
				<c:when test="${scoreList != null && scoreList.size() > 0}">
					<div class="mx-3 mb-2">検索結果: ${scoreList.size()} 件</div>
					<c:if test="${not empty scoreList}">
						<h2>検索結果</h2>
						<table class="table table-hover mx-3">
							<thead>
								<tr>
									<th>順位</th>
									<th>氏名</th>
									<th>最高点</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="score" items="${scoreList}">
									<tr>
										<td>${score.rank}</td>
										<td>${score.studentName}</td>
										<td>
											<c:set var="maxPoint" value="${requestScope['max_point_' + score.studentNo]}" />
											<c:choose>
												<c:when test="${maxPoint == -2147483648}"> <%-- Integer.MIN_VALUE --%>
													- <%-- 点数がない場合はハイフン表示 --%>
												</c:when>
												<c:otherwise>
													<c:out value="${maxPoint}" />
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</c:when>
				<c:when test="${scoreList != null && scoreList.size() == 0}">
					<div class="alert alert-info mx-3">検索条件に該当する成績データは存在しませんでした。</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-secondary mx-3">検索条件を指定して検索ボタンをクリックしてください。</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>
