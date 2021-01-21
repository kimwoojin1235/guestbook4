<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<p>비밀번호가 틀렸습니다.</p>

	<br>

	<a href="${pageContext.request.contextPath}/guest/deleteForm?no=${param.no}">뒤로</a>
</body>
</html>