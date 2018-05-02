<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<c:forEach items="${cashupUsers}" var="tempCashupUser">
	<option value="${tempCashupUser[0]}">${tempCashupUser[1]} ${tempCashupUser[2]}</option>
</c:forEach>