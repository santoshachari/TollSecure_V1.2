<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Operator home page</title>

	<!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    
<link type="text/css" 
      rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/css/style.css"/> 
</head>
<body>
<a href="${pageContext.request.contextPath}/index/logout?User=${user}">logout</a><br>
	Hi
	${userFromSession.userFirstName}<br>
	${userFromSession.userRole}<br>
	${alreadyAssignedError}<br>
	<table>
		<tr>
			<th>LANE DIRECTION</th>
			<th>LANE CODE</th>
		</tr>
		
		<c:forEach var="tempLane" items="${lanes}">
			<tr>
				<c:url var="laneTransactionLink" value="/tollTransaction/tollTransactionForm">
					<c:param name="laneId" value="${tempLane.laneId}"></c:param>
					<c:param name="plazaId" value="${tempLane.tollPlazaId}"></c:param>
				</c:url>
				<td>${tempLane.laneDirection}</td>
				<td><a href="${laneTransactionLink}">${tempLane.laneCode}</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>