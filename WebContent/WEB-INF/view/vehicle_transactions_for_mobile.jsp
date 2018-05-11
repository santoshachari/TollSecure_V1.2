<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<style>

table {   
	border-collapse:collapse;
	border-bottom:1px solid #fff;
	font-family: Tahoma,Verdana,Segoe,sans-serif;
	width:72%;
	color: #ffff;
}
 
th {
	border-bottom:1px solid #fff;
	background:none repeat scroll 0 0 #ee9620;
	padding:10px;
	color: #FFFFFF;
}

tr {
	border-top:1px solid #fff;
	text-align:center;	
}

td {
	color: #000000;
}
</style>

<table width="100%"  border="0" cellspacing="0" cellpadding="0" style="">
	<tr>
		<th >Number </th>
		<!-- <th width="40%">Time Stamp</th>
		<th width="20%">Ticket Code.</th> -->
		<th width="20%">Vehicle N0.</th>
		<th width="30%">Vehicle Class</th>
		<th>Journey Type</th>
		<!-- <th>Amount</th> -->
		<th>Edit</th>
	</tr>
	 <%!
       int count = 0;
   	%>
	<c:forEach items="${tollTransactions}" var="tempTransaction">
		<tr>
			<c:url var="cancelLink" value="/tollTransaction/cancelTicket">
				<c:param name="transactionId" value="${tempTransaction[0]}"></c:param>
			</c:url>
			
			<c:url var="detailsLink" value="/tollTransaction/transactionDetails">
				<c:param name="transactionId" value="${tempTransaction[0]}"></c:param>
				<c:param name="hdate" value="${hdate}"></c:param>
				<c:param name="hstime" value="${hstime}"></c:param>
				<c:param name="hetime" value="${hetime}"></c:param>
				<c:param name="hlane" value="${hlane}"></c:param>
				<c:param name="hvnumber" value="${hvnumber}"></c:param>
				<c:param name="hvclass" value="${hvclass}"></c:param>
			</c:url>
			
			<td style="color: #000000"><% out.print(++count); %></td>  
			<%-- <td style="color: #000000">${tempTransaction[6]}</td>
			<td style="color: #000000">${tempTransaction[2]}</td>  --%>
			<td ><a href="${detailsLink}">${tempTransaction[1]}</a></td>
			<td style="color: #000000">${tempTransaction[3]}</td>
			<td style="color: #000000">${tempTransaction[4]}</td>
			<%-- <td>${tempTransaction[5]}</td> --%>
			<td style="color: #000000"><a href="#" onclick="checkTransactionAndCancel('${tempTransaction[0]}')">Cancel</a></td>
		</tr>
	</c:forEach>
	
</table>

	<%
      count = 0;
   	%>







