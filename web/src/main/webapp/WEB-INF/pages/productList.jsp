<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<p>
  Hello from product list!
</p>
<p>
  Found <c:out value="${phones.size()}"/> phones.
  <table border="1px">
    <thead>
      <tr>
        <td>Image</td>
        <td>Brand</td>
        <td>Model</td>
        <td>Color</td>
        <td>Display size</td>
        <td>Price</td>
      </tr>
    </thead>
    <c:forEach var="phone" items="${phones}">
      <tr>
        <td>
          <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
        </td>
        <td>${phone.brand}</td>
        <td>${phone.model}</td>
        <td>
          <c:forEach items="${phone.colors}" var="color" varStatus="loop">
            ${color.code}
          <c:if test="${!loop.last}">,</c:if>
      </c:forEach></td>
        <td>${phone.displaySizeInches}</td>
        <td>$ ${phone.price}</td>
      </tr>
    </c:forEach>
  </table>
</p>