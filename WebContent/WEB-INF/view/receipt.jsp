<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<c:choose>
		<c:when test="${empty savedOne.transactionId}">
						Did not find transaction in database
		</c:when>
		<c:otherwise>
		<div>VANTADA TOLL PLAZA<div>
		<div>NHAI-NH-8</div>
		<div>Section: Ratanpur - Himatnagar</div>
		<div>KM: 401.200 to KM: 443.000</div>
		<div>Located: KM: 415.000</div>
		<br>-------------------------------------------------<br>
		<span style="margin-left:20px;">M/S KESHAV AGARWAL</span><br>
		-------------------------------------------------
		<div id="rti">Ticket Code. ${savedOne.ticketCode}</div>
		<div>Lane Code(Booth no). ${laneCode}</div>
		<div>Operator Id. ${savedOne.createUserID}</div>
		<div>Date and Time. ${savedOne.createTimeStamp}</div>
		<div id="rvn">Vehicle No. ${savedOne.vehicleNumber}</div>
		<div id="rvc">Type of Vehicle. ${vehicleClass}</div>
		<div id="rjt">Journey Type. 
			<c:choose>
			         <c:when test = "${savedOne.journeyType == 'S'}">
			          	<b>Single</b>
			         </c:when>
			         
			         <c:otherwise>
			         	<b>Return</b>
			         </c:otherwise>
   			</c:choose>
		
		
		</div>
		<div id="ram">Fee. <b>${savedOne.tollAmt}</b></div>
		-------------------------------------------------<br>
		<span style="margin-left:20px;">Wish You A Happy Journey</span><br>
		-------------------------------------------------
		</c:otherwise>
	</c:choose>