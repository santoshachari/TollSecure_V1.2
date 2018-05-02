<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Lanes list</title>

	<!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    
    
<link type="text/css" 
      rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/css/style.css"/> 
</head>
<body>
	Hi ${userFromSession.userFirstName} ${userFromSession.userLastName}

	<table>
	
	<table style="width:100%">
  <tr>
			<th>TOLL PLAZA ID</th>
			<th>LANE DIRECTION</th>
			<th>CREATED BY</th>
			<th>CREATED ON</th>
			<th>MODIFIED BY USER ID</th>
			<th>MODIFIED ON</th>
			<th>LANE TRANSACTION</th>
			<th>MANUAL LANE TRANSACTION</th>
			<th>CASHUP DECLARATION</th>
			<th>ACTION</th>
  </tr>
  	<c:forEach var="tempLane" items="${lanes}">
  		<tr>
		
				<c:url var="updateLink" value="/lane/showFormForUpdate">
					<c:param name="laneId" value="${tempLane.laneId}">
					</c:param>
				</c:url>
				
				<!-- construct an "delete" link with tollPlaza id -->
				<c:url var="deleteLink" value="/lane/delete">
					<c:param name="laneId" value="${tempLane.laneId}">
					</c:param>	
				</c:url>
				
				<!-- getting list of lanes in this toll plaza -->
				<c:url var="lanesLink" value="/lane/list">
					<c:param name="laneId" value="${tempLane.laneId}">
					</c:param>	
				</c:url>  
				
				<!-- For transaction forms -->
				<c:url var="laneTransactionLink" value="/tollTransaction/tollTransactionForm">
					<c:param name="plazaId" value="${tempLane.tollPlazaId}"></c:param>
					<c:param name="laneId" value="${tempLane.laneId}">
					</c:param>
				</c:url>
				
				<!-- For manual transaction forms -->
				<c:url var="manualLaneTransactionLink" value="/tollTransaction/manualEntries">
					<c:param name="plazaId" value="${tempLane.tollPlazaId}"></c:param>
					<c:param name="laneId" value="${tempLane.laneId}">
					</c:param>
				</c:url>
				
				<!-- For cashup declaration forms -->
				<c:url var="cashupDeclarationLink" value="../cashup/declareCash">
					<c:param name="laneDirection" value="${tempLane.laneDirection}"></c:param>
					<c:param name="plazaId" value="${tempLane.tollPlazaId}">
					</c:param>
					<c:param name="laneId" value="${tempLane.laneId}">
					</c:param>
				</c:url>
				
				
				<td>${tempLane.tollPlazaId}</td>
				<td>${tempLane.laneDirection}</td>
				<td>${tempLane.createUserId}</td>
				<td>${tempLane.createTimeStamp}</td>
				<td>${tempLane.modifiedUserId}</td>
				<td>${tempLane.modificationTimeStamp}</td>
				<td><a href="${laneTransactionLink}">${tempLane.laneDirection}</a></td>
				<td><a href="${manualLaneTransactionLink}">${tempLane.laneDirection}</a></td>
				<td><a href="${cashupDeclarationLink}">Declare Cashup</a></td>
				<td>
				<!-- display the update link -->
				<a href="${updateLink}">Update</a>
				|
				<a href="${deleteLink}"
					onclick="if(!(confirm('Are you sure you want to delete this?'))) return false"> Delete </a>
				</td>
			
  		</tr>
  	</c:forEach>
</table>
		<input type="button" value="Add Lane"
		onclick="window.location.href='showFormForAdd?tollPlazaId=${tollPlazaId}'; return false"
		class="add-button"
		/>
		<input type="button" value="Back"
			onclick="window.location.href='../tollPlaza/list'; return false"
			/>
</body>
</html>