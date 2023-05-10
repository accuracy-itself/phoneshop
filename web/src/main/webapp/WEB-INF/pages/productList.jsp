<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<tags:master pageTitle="Product List">
  <form class="search-phone">
      <input name="query" value="${param.query}">
      <button>&#x1F50D;</button>
  </form>
    <div id="success-quantity" class="success"></div>
  <p>
    <table class="product-table">
      <thead>
        <tr>
          <th>Image</th>
          <th>
            Brand
            <tags:sortLink sortOrder="asc" sortField="brand"/>
            <tags:sortLink sortOrder="desc" sortField="brand"/>
          </th>
          <th>
            Model
            <tags:sortLink sortOrder="asc" sortField="model"/>
            <tags:sortLink sortOrder="desc" sortField="model"/>
          </th>
          <th>Color</th>
          <th>
            Display size
            <tags:sortLink sortOrder="asc" sortField="displaySizeInches"/>
            <tags:sortLink sortOrder="desc" sortField="displaySizeInches"/>
          </th>
          <th>
            Price
            <tags:sortLink sortOrder="asc" sortField="price"/>
            <tags:sortLink sortOrder="desc" sortField="price"/>
          </th>
          <th>Quantity</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
      <c:forEach var="phone" items="${phones}">
        <tr class="product-table__row">
          <td>
           <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
          </td>
          <td>${phone.brand}</td>
          <td><a href="<c:url value="/productDetails?id=${phone.id}"/>">${phone.model}</a></td>
          <td>
            <c:forEach items="${phone.colors}" var="color" varStatus="loop">
              ${color.code}
              <span class="color-dot" style="background-color: ${color.code}"></span>
              <c:if test="${!loop.last}">,</c:if>
            </c:forEach>
          </td>
          <td>${phone.displaySizeInches}</td>
          <td class = price>${phone.price} $</td>
          <td class = "quantity-td">
               <input name="quantity" type="number" id="quantity${phone.id}" class="quantity" value="1"
                 oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
               <br>
               <span id="error-quantity${phone.id}" class="error"></span>
          </td>
          <td>
            <button onclick="addToCart(${phone.id})" class="add-button">Add to cart</button>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </p>
  <div class="product-list-pagination">
      <c:set var="pageUrl" scope="session"
        value="productList?query=${param.query}&sortField=${param.sortField}&sortOrder=${param.sortOrder}"/>

      <c:if test="${page > 1}">
        <td><a href="${pageUrl}&page=${page - 1}">&#60;</a></td>
      </c:if>

      <table cellpadding="5" cellspacing="5">
          <tr>
              <c:forEach begin="1" end="${pagesAmount}" var="i">
                  <c:choose>
                      <c:when test="${page eq i}">
                          <td>${i}</td>
                      </c:when>
                      <c:otherwise>
                          <td><a href="${pageUrl}&page=${i}" class="page-number">${i}</a></td>
                      </c:otherwise>
                  </c:choose>
              </c:forEach>
          </tr>
      </table>

      <c:if test="${page < pagesAmount}">
          <td><a href="${pageUrl}&page=${page + 1}">&#62;</a></td>
      </c:if>
  </div>
</tags:master>
