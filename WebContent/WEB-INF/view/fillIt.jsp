<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<c:forEach items="${allShifts}" var="shift">
	<div style='padding: 0% 10% 1% 10%'>
		${shift.shiftDesc} Shift start time: ${shift.startTime}
	    <span class='text-right'> ${shift.shiftDesc} Shift end time: ${shift.endTime}</span>
    </div>						
</c:forEach>