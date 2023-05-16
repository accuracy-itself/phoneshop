<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="user-login">
  <security:authorize access="!isAuthenticated()">
        <a href="<c:url value="/login"/>">Login </a>
  </security:authorize>
  <security:authorize access="isAuthenticated()">
      <security:authentication property="principal.username" />
  </security:authorize>
  <security:authorize access="isAuthenticated() and hasRole('ROLE_ADMIN')">
        <a href="<c:url value="/admin/orders"/>"> admin </a>
  </security:authorize>
  <security:authorize access="isAuthenticated()">
     <a href="<c:url value="/logout"/>"> Logout </a>
  </security:authorize>
</div>