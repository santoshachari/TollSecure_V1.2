<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<c:forEach var="tempLane" items="${lanes}">
	<option value="${tempLane.laneDirection}">${tempLane.laneDirection}</option>
</c:forEach>