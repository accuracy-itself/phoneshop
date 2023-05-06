<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tags:master pageTitle="Order">
  <div class="content">
    <form:form action="order" method="post" modelAttribute="orderDto">

    <table class="product-table">
      <thead>
        <tr>
          <th>Image</th>
          <th>Brand</th>
          <th>Model</th>
          <th>Color</th>
          <th>Display size</th>
          <th>Quantity</th>
          <th>Price</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach var="phoneItem" items="${order.items}" varStatus="status">
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
          <td>
               <form:hidden path="items[${status.index}].phoneId" value="${phoneItem.phone.id}"/>
               <form:hidden path="items[${status.index}].quantity" class="quantity" value="${items[status.index].quantity}"/>
               <br>
               <div class="quantity">${phoneItem.quantity}</div>
               <br>
          </td>
          <td class = "price">${phoneItem.phone.price} $</td>
        </tr>
      </c:forEach>

      </tbody>
    </table>

    <br>
    <div class="price-table customer-details">
        <table>
            <tr class="price-table__row">
              <td>Subtotal</td>
              <td><div class = "price">${order.subtotal}$</div></td>
            </tr>
            <tr class="price-table__row">
               <td>Delivery price</td>
               <td><div class = "price">${order.deliveryPrice}$</div></td>
            </tr>
            <tr class="price-table__row">
               <td>Total price</td>
              <td><div class = "price">${order.totalPrice}$</div></td>
            </tr>
        </table>
    </div>

    <br>
     <h2 class = "customer-details">Your details</h2>
       <table class="customer-details">
           <tr>
               <td>First Name<span style="color:red">*</span></td>
               <td>
                   <form:input path="firstName"
                   value="${firstName}"/>
                   <form:errors path="firstName" class="error"/>
               </td>
           </tr>
           <tr>
               <td>Last Name<span style="color:red">*</span></td>
               <td>
                   <form:input path="lastName"
                   value="${lastName}"/>
                   <form:errors path="lastName" class="error"/>
               </td>
           </tr>
           <tr>
               <td>Phone<span style="color:red">*</span></td>
               <td>
                   <form:input path="contactPhoneNo"
                   value="${contactPhoneNo}"/>
                   <form:errors path="contactPhoneNo" class="error"/>
               </td>
           </tr>
           <tr>
               <td>Delivery address<span style="color:red">*</span></td>
               <td>
                   <form:input path="deliveryAddress"
                   value="${deliveryAddress}"/>
                   <form:errors path="deliveryAddress" class="error"/>
               </td>
           </tr>
       </table>
    <br>
    <input type="submit" value="Order" class="button">
    </form:form>
  </div>
</tags:master>