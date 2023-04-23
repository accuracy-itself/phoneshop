<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
  <title>${pageTitle}</title>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
  <spring:url value="/resources/styles/main.css" var="mainCss" />
  <link href="${mainCss}" rel="stylesheet" />
</head>
<body>
  <header >
  <div class="header">
  <a href="productList?">
      <img src="${pageContext.servletContext.contextPath}/resources/images/icon.png" alt="no image"/>
  </a>
  <br>
  <div>Cart:
    <span id="cart-quantity">${cart.totalQuantity}</span>
    items, cost
    <span id="cart-cost">${cart.totalCost}</span> $
  </div>
  </div>
      <hr />
  </header>
  <main>
    <jsp:doBody/>
  </main>
  <p>
    (c) Expert-Soft
  </p>
</body>
</html>
