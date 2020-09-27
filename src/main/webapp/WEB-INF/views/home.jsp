<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<title>Spring-Security 5 (Java 방식)</title>
	<meta charset="UTF-8">
	<style>
		body{
			font-family:'Arial';
			font-size:12px;
		}

		a{
			text-decoration:none;
			color:#666;
		}
	</style>
</head>
<body>
<h1>
	Hello world!(Spring-Security 5(Java 방식)) - DB연동(Oracle)
</h1>
<hr />
<sec:authorize access="isAnonymous()">
	<!-- 로그인 전 -->
	<p>
		<a href="<c:url value="/member/loginForm" />">로그인</a>
	</p>
</sec:authorize> 
<sec:authorize access="isAuthenticated()">
	<!-- 로그인 성공 -->
	<form:form action="${pageContext.request.contextPath}/logout" method="POST">
		<input type="submit" value="로그아웃" />
	</form:form>
	
	<p>
		<!-- 방법1. Sec 적용(이름 출력) -->
		방법(sec 태그)1: <sec:authentication property="name" />
	</p>
	<p>
		<!-- 방법2. c태그, Controller에서 가져오기 -->
		방법(Model 정의)2: ${username}
	</p>
</sec:authorize> 

<h3>
<!-- 관리자 권한을 가진 경우만 보이기 -->
<sec:authorize access="hasRole('ROLE_ADMIN')" >
	<a href="<c:url value="/admin/home" />">관리자 홈</a>&nbsp;&nbsp;
</sec:authorize>
	<a href="<c:url value="/encode-password?password=pass" />">비밀번호</a>
</h3>

<!-- 비밀번호 생성기 -->
<c:set var="gene_pwd" value="${encode}" />
<c:if test="${gene_pwd != null}">
    <c:out value="${gene_pwd}" />
</c:if>

</body>
</html>
