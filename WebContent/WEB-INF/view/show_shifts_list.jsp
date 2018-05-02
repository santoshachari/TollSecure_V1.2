<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Shifts List</title>

<link type="text/css" 
      rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/css/style.css"/> 

</head>
<body>
		<c:choose>
		<c:when test="${empty userFromSession.userFirstName}">
						Hi all
			<% response.sendRedirect("../index/loginUser"); %> 
		</c:when>
		<c:otherwise>
			Hi <br>
			Name: ${userFromSession.userFirstName}
			${userFromSession.userLastName}<br>
			Role: ${userFromSession.userRole}
			<a href="${pageContext.request.contextPath}/index/logout?User=${user}">logout</a>
		</c:otherwise>
	</c:choose>
	<table>
	
	<table style="width:100%">
  <tr>
			<th>TOLL PLAZA ID</th>
			<th>SHIFT DESC</th>
			<th>START TIME</th>
			<th>END TIME</th>
			<th>CREATED BY</th>
			<th>CREATED ON</th>
			<th>MODIFIED BY</th>
			<th>MODIFIED ON</th>
  </tr>
  <c:forEach var="tempShift" items="${shifts}">
  		<tr>
  			<td>${tempShift.tollPlazaId}</td>
  			<td>${tempShift.shiftDesc}</td>
  			<td>${tempShift.startTime}</td>
  			<td>${tempShift.endTime}</td>
  			<td>${tempShift.createUserId}</td>
  			<td>${tempShift.createTimeStamp}</td>
  			<td>${tempShift.modifiedUserId}</td>
  			<td>${tempShift.modificationTimeStamp}</td>
    	</tr>
  </c:forEach>
  
  <input type="button" value="Reconfigure"
			onclick="window.location.href='shiftConfigure?plazaId=${tollPlazaId}'; return false"
			class="add-button"
			/>
	
 	<input type="button" value="Back"
			onclick="window.location.href='../tollPlaza/list'; return false"
			/>
</body>
</html>