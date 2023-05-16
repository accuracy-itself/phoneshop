<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tags:master pageTitle="Order">
  <div class="content">
    <div class="error">${error_order}</div>
    <c:if test="${empty error_order}"><div>
    <div> Order number: ${order.id}
        Status: <span id="order-status">${order.status}</span>
    </div>
    <br>
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
     <h3 class = "customer-details">Customer details</h3>
     <table class="customer-details">
         <tr>
             <td>First Name</td>
             <td>${order.firstName}</td>
         </tr>
         <tr>
             <td>Last Name</td>
             <td>${order.lastName}</td>
         </tr>
         <tr>
             <td>Phone</td>
             <td>${order.contactPhoneNo}</tr>
         <tr>
             <td>Delivery address</td>
             <td>${order.deliveryAddress}</td>
         </tr>
     </table>
    <br>
    <a href="../orders">Back</a>
    <button onclick="changeOrderStatus(${order.id}, 'DELIVERED')">Delivered</button>
    <button onclick="changeOrderStatus(${order.id}, 'REJECTED')">Rejected</button>
    <div id="success-update" class="success"></div>
    <div id="wrong-update" class="error"></div>
    </div>
    </c:if>
  </div>
</tags:master>