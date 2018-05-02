<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>

table {   
	border-collapse:collapse;
	border-bottom:1px solid #fff;
	font-family: Tahoma,Verdana,Segoe,sans-serif;
	width:72%;
	color: #ffff;
	font-size: 0.9em;
}
 
th {
	border-bottom:1px solid #fff;
	background:none repeat scroll 0 0 #ee9620;
	padding:10px;
	color: #FFFFFF;
	font-weight: lighter;
}

tr {
	border-top:1px solid #fff;
	text-align:center;	
}
</style>

<table>
	<tr>
		<th>Vehicle Number</th>
		<th>Pass No.</th>
		<th>Vehicle_Cls</th>
		<th>Issue_Dt</th>
		<th>Expiry_Dt</th>
		<th>Pass_Type</th>
		<th>Amount</th>
	</tr>
	
	<c:set var="count" value="1"/>
   	
	<c:forEach items="${passes}" var="tempPass" varStatus="passCounter">
	
		<c:set var="string1" value="${fn:split(tempPass.effectiveFrom, ' ')}" />
		<c:set var="string2" value="${fn:split(tempPass.effectiveTo, ' ')}" />

		<c:set var="onlyFromDate" value="${string1[0]}" />
		<c:set var="onlyToDate" value="${string2[0]}" />
		
		<c:set var="fromDateSplit" value="${fn:split(onlyFromDate, '-')}"/>
		<c:set var="toDateSplit" value="${fn:split(onlyToDate, '-')}"/>
		
		<c:set var="fromYear" value="${fn:substring(fromDateSplit[0], 2, 4)}"/>
		<c:set var="fromMonth" value="${fromDateSplit[1]}"/>
		<c:set var="fromDate" value="${fromDateSplit[2]}"/>
		
		<c:set var="toYear" value="${fn:substring(toDateSplit[0], 2, 4)}"/>
		<c:set var="toMonth" value="${toDateSplit[1]}"/>
		<c:set var="toDate" value="${toDateSplit[2]}"/>

		
	
		<tr style="color: #fff">
			<c:choose>
				<c:when test="${fn:length(passes) gt count}">
					<td>${tempPass.vehicleNumber} </td>
					<td>${tempPass.passId}</td>
					<td>${tempPass.vehicleClass}</td>
					<%-- <td>${tempPass.effectiveFrom}</td> --%>
					<td><c:out value="${fromDate}-${fromMonth}-${fromYear}"></c:out></td>
					<td><c:out value="${toDate}-${toMonth}-${toYear}"></c:out></td>
					<%-- <td>${tempPass.effectiveTo}</td> --%>
					<td>${tempPass.passType}</td>
					<td>${tempPass.amount}</td>
				</c:when>
				<c:otherwise>
					<td>${tempPass.vehicleNumber} </td>
					<td  onclick="autoShowModal(${tempPass.passId})" style="cursor: pointer; color: blue">${tempPass.passId}</td>
					<td>${tempPass.vehicleClass}</td>
					<%-- <td>${tempPass.effectiveFrom}</td> --%>
					<td><c:out value="${fromDate}-${fromMonth}-${fromYear}"></c:out></td>
					<td><c:out value="${toDate}-${toMonth}-${toYear}"></c:out></td>
					<%-- <td>${tempPass.effectiveTo}</td> --%>
					<td>${tempPass.passType}</td>
					<td>${tempPass.amount}</td>
				</c:otherwise>
			</c:choose>
			<c:set var="count" value="${count + 1}" scope="page"/>
		</tr>
	</c:forEach>
		
</table>
