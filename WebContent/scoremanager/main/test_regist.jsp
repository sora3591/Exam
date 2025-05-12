<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

            <form action="TestRegist.action" method="get">

    <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
        <div class="col-4">
            <label class="form-label" for="student-f1-select">入学年度</label>
            <select class="form-select" id="student-f1-select" name="f1">
                <option value="0">--------</option>
                <c:forEach var="year" items="${ent_year_set}">
                    <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                </c:forEach>
            </select>
        </div>

        <div class="col-4">
            <label class="form-label" for="student-f2-select">クラス</label>
            <select class="form-select" id="student-f2-select" name="f2">
                <option value="0">--------</option>
                <c:forEach var="num" items="${class_num_set}">
                    <option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
                </c:forEach>
            </select>
        </div>

        <div class="col-4">
            <label class="form-label" for="student-f3-select">科目</label>
            <select class="form-select" id="student-f3-select" name="f3">
                <option value="0">--------</option>
                <c:forEach var="subjectname" items="${subjectname}">
                    <option value="${subjectname}" <c:if test="${subjectname == f3}">selected</c:if>>${subjectname}</option>
                </c:forEach>
            </select>
        </div>

        <div class="col-4">
            <label class="form-label" for="student-f4-select">回数</label>
            <select class="form-select" id="student-f4-select" name="f4">
                <option value="0">--------</option>
                <option value="1" <c:if test="${f4 == 1}">selected</c:if>>1</option>
                <option value="2" <c:if test="${f4 == 2}">selected</c:if>>2</option>
            </select>
        </div>
    </div>

    <c:if test="${filterError && searchPerformed}">
    <div class="alert alert-danger mx-3" role="alert">
        入学年度、クラス、科目、回数をすべて選択してください。
    </div>
	</c:if>

    <div class="col-2 text-center">
        <button class="btn btn-secondary" id="filter-button">検索</button>
    </div>
</form>

<c:if test="${not empty student}">
    <p>科目: ${f3}（${f4}回）</p>
    <form action="TestRegistExecute.action" method="get">
        <input type="hidden" name="f1" value="${f1}">
        <input type="hidden" name="f2" value="${f2}">
        <input type="hidden" name="f3" value="${f3}">
        <input type="hidden" name="f4" value="${f4}">
        <input type="hidden" name="subjectcd" value="${subjectcd}">

        <table class="table table-hover">
            <tr>
                <th>入学年度</th>
                <th>学生番号</th>
                <th>氏名</th>
                <th>クラス</th>
                <th>点数</th>
            </tr>
            <c:forEach var="student" items="${student}">
                <tr>
                    <td>${student.entYear}</td>
                    <td>${student.no}</td>
                    <td>${student.name}</td>
                    <td>${student.classNum}</td>
                    <td>
                        <input class="form-control" autocomplete="off" maxlength="3"
                               name="point_${student.no}" type="text"
                               value="${test.point}" required pattern="^(100|[1-9]?[0-9])$"
                               title="0~100の範囲で入力してください" />
                    </td>
                </tr>
            </c:forEach>
        </table>

        <div class="col-2 text-center">
            <button class="btn btn-secondary" id="submit-button">登録して終了</button>
        </div>
    </form>
</c:if>
</section>
</c:param>

</c:import>