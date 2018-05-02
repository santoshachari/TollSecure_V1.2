<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<c:choose>
		<c:when test="${empty ticket}">
				Did not find transaction in database
		</c:when>
		<c:otherwise>
			<div id="v">${ticket.vehicleNumber}</div>
			<div id="t">${ticket.ticketCode}</div>
			<div id="u">${user.userFirstName} ${user.userLastName}</div>
			<div id="l">${lane.laneCode}</div>
			<div id="a">${ticket.tollAmt}</div>
			<div id="j">${ticket.journeyType}</div>
			<div id="i">${ticket.imageAddress}</div>
		</c:otherwise>
	</c:choose>