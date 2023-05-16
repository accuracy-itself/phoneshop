<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
<head>
  <title>${pageTitle}</title>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
  <spring:url value="/resources/styles/main.css" var="mainCss" />
  <link href="${mainCss}" rel="stylesheet" />
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <script src="<c:url value="/resources/scripts/addProductToCart.js"/>"></script>
  <script src="<c:url value="/resources/scripts/changeOrderStatus.js"/>"></script>
</head>
<body>
  <header >
  <div class="header">
  <a href="<c:url value="/productList"/>">
      <img src="${pageContext.servletContext.contextPath}/resources/images/icon.png" alt="no image"/>
  </a>
  <br>
  <div>
    <a href="<c:url value="/cart"/>">
      Cart:
      <span id="cart-quantity">${cart.totalQuantity}</span>
      items, cost
      <span id="cart-cost">${cart.totalCost}</span> $
    </a>
  </div>
  </div>
      <hr />

  <tags:login/>

  </header>
  <main>
    <jsp:doBody/>
  </main>
  <p>
    (c) Expert-Soft
  </p>
</body>
</html>
