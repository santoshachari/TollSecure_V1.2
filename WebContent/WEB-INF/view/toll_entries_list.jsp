<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<head>
<title>List all the toll entries</title>
<!-- reference our css file -->
<link type="text/css"
	  rel = "stylesheet"
	  href="${pageContext.request.contextPath}/resources/css/style.css"
/>


</head>
Hi ${userFromSession.userFirstName} ${userFromSession.userLastName}
<body>
	<div id="wrapper">
		<div id="header">
			<h2>Toll Gate Receipt</h2>
		</div>
	</div>
	
	<div id="container">
		<div id="content">
			
			<!-- put new button: Add Transaction -->
			<input type="button" value="Add Transaction"
								  onclick="window.location.href='tollTransactionForm'; return false;"
								  class="add-button"
			/>
			
			<!-- add out html table here -->
			<table>
				<tr>
					<th>TRANSACTION ID</th>
					<th>VEHICLE_NUMBER</th>
					<th>TRANSACTION TIMESTAMP</th>
					<th>CONCESSION TYPE</th>
					<th>TOLL AMT</th>
				</tr>
				
				<!-- Loop over and print our customers -->
				<c:forEach var="tempTransaction" items="${tollTransactions}">
					<tr>
						<td>${tempTransaction.transactionId}</td>
						<td>${tempTransaction.vehicleNumber}</td>
						<td>${tempTransaction.transactionTimeStamp}</td>
						<td>${tempTransaction.concessionType}</td>
						<td>${tempTransaction.tollAmt}</td>
					</tr>
				</c:forEach>
			</table>
			
			
			<table>
				<tr>
					<th>Vehicle Class Id</th>
					<th>Vehicle Class</th>
					<th>Journey Type</th>
					<th>Toll Amt</th>
				</tr>
				
				<!-- Loop over and print our customers -->
				<c:forEach var="tempConfig" items="${tollConfigs}">
					<tr>
						<td>${tempConfig.vehicleClassId}</td>
						<td>${tempConfig.vehicleClass}</td>
						<td>${tempConfig.journeyType}</td>
						<td>${tempConfig.tollAmt}</td>
					</tr>
				</c:forEach>
			</table>
			
		</div>
	</div>
</body>
</html>