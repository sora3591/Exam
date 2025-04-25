<%-- 科目別テスト一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム - 科目別テスト一覧
	</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目別テスト一覧</h2>
			<form method="post" action="TestListSubjectExecute.action">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-3">
						<label class="form-label" for="entYear-select">入学年度</label>
						<select class="form-select" id="entYear-select" name="entYear">
							<option value="0">--------</option>
							<c:forEach var="year" items="${entYearList}">
								<option value="${year}" <c:if test="${year==selectedEntYear}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2">
						<label class="form-label" for="class-select">クラス</label>
						<select class="form-select" id="class-select" name="classNum">
							<option value="">--------</option>
							<c:forEach var="classNum" items="${classNumList}">
								<option value="${classNum}" <c:if test="${classNum==selectedClassNum}">selected</c:if>>${classNum}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-3">
						<label class="form-label" for="subject-select">科目</label>
						<select class="form-select" id="subject-select" name="subjectCd">
							<option value="">--------</option>
							<c:forEach var="subject" items="${subjectList}">
								<option value="${subject.cd}" <c:if test="${subject.cd==selectedSubjectCd}">selected</c:if>>${subject.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-3">
						<label class="form-label" for="school-select">学校</label>
						<select class="form-select" id="school-select" name="schoolCd">
							<option value="">--------</option>
							<c:forEach var="school" items="${schoolList}">
								<option value="${school.cd}" <c:if test="${school.cd==selectedSchoolCd}">selected</c:if>>${school.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-1 text-center">
						<button type="submit" class="btn btn-primary" id="search-button">検索</button>
					</div>
					<div class="mt-2 text-warning">
						${errors.get("entYear") }
						${errors.get("classNum") }
						${errors.get("subjectCd") }
						${errors.get("schoolCd") }
					</div>
				</div>
			</form>

			<c:choose>
				<c:when test="${testListSubjects != null && testListSubjects.size() > 0}">
					<div class="mx-3 mb-2">検索結果: ${testListSubjects.size()} 件</div>
					<table class="table table-hover mx-3">
						<thead>
							<tr>
								<th>学生番号</th>
								<th>学生氏名</th>
								<th>クラス</th>
								<c:forEach var="testNum" items="${testNumberList}">
									<th>${testNum}回目</th>
								</c:forEach>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="student" items="${testListSubjects}">
								<tr>
									<td>${student.studentNo}</td>
									<td>${student.studentNum}</td>
									<td>${student.classNum}</td>
									<c:forEach var="testNum" items="${testNumberList}">
										<td>
											<c:set var="point" value="${student.points[testNum]}" />
											<c:choose>
												<c:when test="${point != null}">
													<c:choose>
														<c:when test="${point >= 60}">
															<span class="text-success">${point}</span>
														</c:when>
														<c:otherwise>
															<span class="text-danger">${point}</span>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>-</c:otherwise>
											</c:choose>
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:when test="${testListSubjects != null && testListSubjects.size() == 0}">
					<div class="alert alert-info mx-3">検索条件に該当するテストデータは存在しませんでした。</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-secondary mx-3">検索条件を指定して検索ボタンをクリックしてください。</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>