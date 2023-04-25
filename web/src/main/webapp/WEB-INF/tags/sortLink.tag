<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sortOrder" required="true" %>
<%@ attribute name="sortField" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<a href="?sortField=${sortField}&sortOrder=${sortOrder}&query=${param.query}">
<c:if test="${sortOrder == 'asc'}" >&uarr;
</c:if>
<c:if test="${sortOrder == 'desc'}" >&darr;
</c:if>
</a>