<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<div id="ln">
	<c:forEach var="tempLane" items="${allLanes}">
		<option value="${tempLane.laneId}">${tempLane.laneCode}</option>
	</c:forEach>
</div>
<div id="sft">
	<c:forEach var="tempShift" items="${allShifts}">
		<option value="${tempShift.shiftId}">${tempShift.shiftDesc}</option>
	</c:forEach>
</div>
<div id="usr">
	<c:forEach var="tempUser" items="${allUsers}">
		<option value="${tempUser.userId}">${tempUser.userFirstName}${tempUser.userLastName}</option>
	</c:forEach>
</div>