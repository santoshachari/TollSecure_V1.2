<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %> 

<c:choose>
		<c:when test="${empty rateFromDb}">
			 { = } 
		</c:when>
		<c:otherwise>
			 ${rateFromDb}
		</c:otherwise>
	</c:choose> 
