<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:choose>
    <c:when test="${type ==  'html'}">
       <a href="${pageContext.request.contextPath}/JasperReports/${action}.pdf" download="${action}.pdf">Download PDF</a>
    </c:when>
    <c:otherwise>

    </c:otherwise>
</c:choose>
	<iframe src="${pageContext.request.contextPath}/JasperReports/${action}.${type}" width="100%" height="1080px">
	
	</iframe>
</body>
</html>