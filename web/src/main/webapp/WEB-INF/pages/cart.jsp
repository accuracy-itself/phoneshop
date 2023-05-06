<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tags:master pageTitle="Cart">
  <div class="cart-content">
    <div class="error">
        <c:if test="${!cart_error}">${cart_error}</c:if>
    </div>
    <form:form action="cart" method="post" modelAttribute="cartDto">
    <table class="product-table">
      <thead>
        <tr>
          <th>Image</th>
          <th>Brand</th>
          <th>Model</th>
          <th>Color</th>
          <th>Display size</th>
          <th>Price</th>
          <th>Quantity</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
      <c:forEach var="phoneItem" items="${cart.items}" varStatus="status">
        <tr class="product-table__row">
          <td>
           <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phoneItem.phone.imageUrl}"
           alt = "${phoneItem.phone.model}">
          </td>
          <td>${phoneItem.phone.brand}</td>
          <td><a href="<c:url value="/productDetails?id=${phoneItem.phone.id}"/>">${phoneItem.phone.model}</a></td>
          <td>
            <c:forEach items="${phoneItem.phone.colors}" var="color" varStatus="loop">
              ${color.code}
              <span class="color-dot" style="background-color: ${color.code}"></span>
              <c:if test="${!loop.last}">,</c:if>
            </c:forEach>
          </td>
          <td>${phoneItem.phone.displaySizeInches}</td>
          <td class = price>${phoneItem.phone.price} $</td>
          <td>
               <br>
               <form:hidden path="items[${status.index}].id" value="${phoneItem.phone.id}"/>
               <form:input path="items[${status.index}].quantity" class="quantity" value="${items[status.index].quantity}"
                    oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
               <br>
               <form:errors path="items[${status.index}].quantity" class="error"/>
               <div class="error">${error_quantities[phoneItem.phone.id]}</div>
               <br>
          </td>
          <td>
            <button formaction="<c:url value="/cart/delete/${phoneItem.phone.id}"/>" class="delete-button">Delete</button>
          </td>
        </tr>
      </c:forEach>
      
      </tbody>
    </table>
    <br>
    <div>
    <input type="submit" value="Update" class="button">
    </div>
    </form:form>

    <form action="<c:url value="/order"/>">
        <button>Order</button>
    </form>
    <form id="deleteCartItem" method="post">
      </form>
  </div>
</tags:master>