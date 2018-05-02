<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<c:choose>
		<c:when test="${empty savedOne.passId}">
						Did not find transaction in database
		</c:when>
		<c:otherwise>
		-------------------------------------------------<br>
		<span style="margin-left:20px;">M/S KESHAV AGARWAL</span><br>
		-------------------------------------------------
		<div>Pass No. ${savedOne.passId}</div>
		<div>Vehicle No. ${savedOne.vehicleNumber}</div>
		<div>Vehicle Class. ${savedOne.vehicleClass}</div>
		<div>Issue Date. ${savedOne.effectiveFrom}</div>
		<div>Expiry Date. ${savedOne.effectiveTo}</div>
		<div>Amount. ${savedOne.amount}</div>
		-------------------------------------------------<br>
		<span style="margin-left:20px;">Wish You Happy Journey</span><br>
		-------------------------------------------------
		</c:otherwise>
	</c:choose>