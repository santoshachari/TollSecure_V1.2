<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Toll Configuration List</title>

<link type="text/css" 
      rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/css/style.css"/> 

</head>
<body>
	
	Hi ${userFromSession.userFirstName} ${userFromSession.userLastName}
	<input type="button" value="Back" class="inp1"
					onclick="window.location.href='../tollPlaza/list'; return false"
					class="Re-Configure Shifts"
					/>
	<table style="width:100%">
		<tr>
			<th>VEHICLE CLASS</th>
		  	<th>TOLL PLAZA ID</th>
			<th>JOURNEY TYPE</th>
			<th>TOLL AMOUNT</th>
			<th>EFFECTIVE FROM</th>
			<th>EFFECTIVE TO</th>
			
			<th>CREATED BY</th>
			<th>CREATED ON</th>
			<th>MODIFIED BY</th>
			<th>MODIFIED ON</th>
			<th>ACTION</th>
	  </tr>
	 <c:forEach var="tempConfig" items="${tollConfigs}">  	
	  <tr>
				<c:url var="updateLink" value="/tollConfig/showFormForUpdate">
					<c:param name="vehicleClassId" value="${tempConfig.vehicleClassId}">
					</c:param>
				</c:url>
				
				<!-- construct an "delete" link with tollPlaza id -->
				<c:url var="deleteLink" value="/tollConfig/delete">
					<c:param name="vehicleClassId" value="${tempConfig.vehicleClassId}">
					</c:param>	
				</c:url>
			
				<td>${tempConfig.vehicleClass}</td>
				<td>${tempConfig.tollPlazaId}</td>
				<td>${tempConfig.journeyType}</td>
				<td>${tempConfig.tollAmt}</td>
				<td>${tempConfig.effectiveFrom}</td>
				<td>${tempConfig.effectiveTo}</td>
				
				<td>${tempConfig.createUserId}</td>
				<td>${tempConfig.createTimeStamp}</td> 
				<td>${tempConfig.modifiedUserId}</td>
				<td>${tempConfig.modificationTimeStamp}</td>
				<!-- display the update link -->
				<td>
				<a href="${updateLink}">Update</a>
				<%-- |
				<a href="${deleteLink}"
					onclick="if(!(confirm('Are you sure you want to delete this?'))) return false"> Delete </a> --%>
				</td>
			</tr>
	  </c:forEach>
  
</body>
</html>