<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>TollPlaza List</title>
<!-- reference our style sheet -->
<link type="text/css" 
      rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/css/style.css"/> 
</head>
<body>
Hi ${userFromSession.userFirstName} ${userFromSession.userLastName}
<input type="button" value="Back" class="inp1"
					onclick="window.location.href='${pageContext.request.contextPath}/index/h0me'; return false"
					class="Re-Configure Shifts"
					/>
	<table style="width:100%">
		<tr>
			<th>TOLL PLAZA NAME</th>
			<th>CREATED BY USER ID</th>
			<th>CREATED ON</th>
			<th>MODIFIED BY USER ID</th>
			<th>MODIFIED ON</th>
			<th>ADD OPERATOR</th>
			<th>GET LANES</th>
			<th>CONFIGURE SHIFTS</th>
			<th>GET TOLL CONFIGURATION</th>
			<th>ACTION</th>
		</tr>
		<c:forEach var="tempTollPlaza" items="${tollPlazas}">
			<tr>
			
			
				<c:url var="updateLink" value="/tollPlaza/showFormForUpdate">
					<c:param name="plazaId" value="${tempTollPlaza.tollPlazaId}">
					</c:param>
				</c:url>
				
				<!-- construct an "delete" link with tollPlaza id -->
				<c:url var="deleteLink" value="/tollPlaza/delete">
					<c:param name="plazaId" value="${tempTollPlaza.tollPlazaId}">
					</c:param>	
				</c:url>
				
				<!-- Add Operator Form -->
				<c:url var="addOperator" value="/index/addOperator">
					<c:param name="plazaId" value="${tempTollPlaza.tollPlazaId}">
					</c:param>	
				</c:url>
				
				<!-- getting list of lanes in this toll plaza -->
				<c:url var="lanesLink" value="/lane/list">
					<c:param name="plazaId" value="${tempTollPlaza.tollPlazaId}">
					</c:param>	
				</c:url>
				
				<!-- configuring shifts for this toll plaza -->
				<c:url var="shiftConfLink" value="/shift/list">
					<c:param name="plazaId" value="${tempTollPlaza.tollPlazaId}">
					</c:param>	
				</c:url> 
				
				<!-- configuring the toll_config -->
				<c:url var="tollConfLink" value="/tollConfig/list">
					<c:param name="plazaId" value="${tempTollPlaza.tollPlazaId}">
					</c:param>	
				</c:url> 
				
			
				<td>${tempTollPlaza.tollPlazaName}</td>
				<td>${tempTollPlaza.createUserId}</td>
				<td>${tempTollPlaza.createTimeStamp}</td>
				<td>${tempTollPlaza.modifiedUserId}</td>
				<td>${tempTollPlaza.modificationTimeStamp}</td>
				<td> <a href="${addOperator}">Add an operator</a>
				<td> <a href="${lanesLink}"> Get all lanes in this TollPlaza</a></td>
				<td><a href="${shiftConfLink}">TollPlaza Shifts</a></td>
				<td><a href="${tollConfLink}">TollPlaza Configuration</a></td>
				<td>
					<!-- display the update link -->
					<a href="${updateLink}">Update</a>
					<%-- |
					<a href="${deleteLink}"
					onclick="if(!(confirm('Are you sure you want to delete this?'))) return false"> Delete </a> --%>
				</td>
			</tr>
		</c:forEach>
		</table>
			<input type="button" value="Add TollPlaza"
			onclick="window.location.href='showFormForAdd'; return false"
			class="add-button"
			/>
</body>
</html>