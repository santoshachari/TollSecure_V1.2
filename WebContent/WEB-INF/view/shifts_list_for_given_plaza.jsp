<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<c:forEach var="tempShift" items="${allShifts}">
	<option value="${tempShift.shiftId}">${tempShift.shiftDesc}</option>
</c:forEach>