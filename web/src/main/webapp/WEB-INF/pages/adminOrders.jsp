<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<tags:master pageTitle="Order List">
  <p>
    <table class="product-table">
      <thead>
        <tr>
          <th>Order number</th>
          <th>Customer</th>
          <th>Phone</th>
          <th>Address</th>
          <th>Total price</th>
          <th>Status</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach var="order" items="${orders}">
        <tr class="product-table__row">
          <td>
           <a href="orders/${order.id}">${order.id}</a>
          </td>
          <td>${order.firstName} ${order.lastName}</td>
          <td>${order.contactPhoneNo}</td>
          <td>${order.deliveryAddress}</td>
          <td>${order.totalPrice} $</td>
          <td>${order.status}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </p>
</tags:master>
