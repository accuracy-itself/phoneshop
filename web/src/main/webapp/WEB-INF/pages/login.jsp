<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Login page">
<html>
<head></head>
<body>
   <form name='f' action="login" method='POST'>
   <div class="login-content">
      <label><h2>
        Login
      </h2></label>

      <div class="login-input">
        <label>
            <b>User Name</b>
        </label>
        <input type='text' name='username' value=''>
      </div>
      <br><br>

      <div class="login-input">
        <label>
            <b>Password</b>
        </label>
        <input type='password' name='password' />
      </div>

      <br><br>
      <input name="submit" type="submit" value="Login" class="button"/>
      <br><br>
  </div>
  </form>

  <div class="login-error">
  <c:if test="${param.error eq true}">
      <div class="error">
          Wrong credentials.
      </div>
  </c:if>
  </div>
</body>
</html>

</tags:master>