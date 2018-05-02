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
	color: #000;
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
</style>

<table>
	<tr>
		<th>S. No. </th>
		<th>Date</th>
		<th>Vehicle Class</th>
		<th>Vehicle Number</th>
		<th>Pass Type</th>
		<th>Shift</th>
		<th>Period</th>
		<th>Amount</th>
	</tr>
	 <%!
       int count = 0;
   	%>
<c:set var="total" value="${0}" />
<c:forEach items="${passShifts}" var="tempPassShift">
<c:set var="total" value="${total + tempPassShift[5]}" />
	<tr>
		<td><% out.print(++count); %></td>  
		<td>${tempPassShift[0]}</td>
		<td>${tempPassShift[1]}</td>
		<td>${tempPassShift[2]}</td>
		<td>${tempPassShift[6]}</td>
		<td>${tempPassShift[3]}</td>
		<td>${tempPassShift[4]}</td>
		<td>${tempPassShift[5]}</td>
	</tr>
</c:forEach>

</table>
<span style="margin-left: 53%"><b>Total Pass Amount: ${total}</b></span>
	<%
      count = 0;
   	%>















