<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    
	<title>${action}</title>

	<style>
		body, html {width: 100%; height: 100%; margin: 0; padding: 0}
		iframe {display: block; width: 100%; height: 95vh;  border: none;} 
	</style>

</head>



<body>
<c:choose>
    <c:when test="${type ==  'html'}">
       <a href="${pageContext.request.contextPath}/JasperReports/${action}.pdf" download="${action}.pdf">Download PDF</a>
    </c:when>
    <c:otherwise>

    </c:otherwise>
</c:choose>
	<iframe src="${pageContext.request.contextPath}/JasperReports/${action}.${type}">
	
	</iframe>
</body>
</html>