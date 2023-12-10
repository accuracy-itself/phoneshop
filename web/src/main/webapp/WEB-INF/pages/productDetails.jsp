<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<tags:master pageTitle="Product Details">
  <div class="product-description">
    <div class="phone-column">
    </div>
    <div class="phone-column">
    </div>
    <div class="phone-column">
      <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
      <div>${phone.description}</div>
      <br>
        <input name="quantity" type="number" id="quantity${phone.id}" class="quantity" value="1"
          oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
        <br>
        <span id="error-quantity${phone.id}" class="error"></span>
        <span id="success-quantity${phone.id}" class="success"></span>
        <br>
      <button onclick="addToCart(${phone.id})" class="add-button">Add to cart</button>
      <br>
      <br>
      <table class="product-table">
          <thead>
            <tr>
              <th>Dimensions&Weight</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr class="product-table__row">
              <td>Length</td>
              <td>${phone.lengthMm}</td>
            </tr>
            <tr class="product-table__row">
              <td>Width</td>
              <td>${phone.widthMm}</td>
            </tr>
            <tr class="product-table__row">
              <td>Weight</td>
              <td>${phone.weightGr}</td>
            </tr>
          </tbody>
      </table>

      <br>
      <table class="product-table">
          <thead>
            <tr>
              <th>Camera</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr class="product-table__row">
              <td>Front</td>
              <td>${phone.frontCameraMegapixels}</td>
            </tr>
            <tr class="product-table__row">
              <td>Back</td>
              <td>${phone.backCameraMegapixels}</td>
            </tr>
          </tbody>
      </table>
    </div>
    <div class="phone-column">
    </div>
    <div class="phone-column">
      <table class="product-table">
         <thead>
           <tr>
             <th>Display</th>
             <th></th>
           </tr>
         </thead>
         <tbody>
           <tr class="product-table__row">
             <td>Size</td>
             <td>${phone.displaySizeInches}</td>
           </tr>
           <tr class="product-table__row">
             <td>Resolution</td>
             <td>${phone.displayResolution}</td>
           </tr>
           <tr class="product-table__row">
             <td>Technology</td>
             <td>${phone.displayTechnology}</td>
           </tr>
           <tr class="product-table__row">
             <td>Pixel Density</td>
             <td>${phone.pixelDensity}</td>
           </tr>
         </tbody>
      </table>

      <br>
      <table class="product-table">
         <thead>
           <tr>
             <th>Battery</th>
             <th></th>
           </tr>
         </thead>
         <tbody>
           <tr class="product-table__row">
             <td>Talk time</td>
             <td>${phone.talkTimeHours} hours</td>
           </tr>
           <tr class="product-table__row">
             <td>Stand by time</td>
             <td>${phone.standByTimeHours} hours</td>
           </tr>
           <tr class="product-table__row">
             <td>Battery capacity</td>
             <td>${phone.batteryCapacityMah} mAh</td>
           </tr>
         </tbody>
      </table>

      <br>
      <table class="product-table">
         <thead>
           <tr>
             <th>Other</th>
             <th></th>
           </tr>
         </thead>
         <tbody>
           <tr class="product-table__row">
             <td>Colors</td>
             <td>
               <c:forEach items="${phone.colors}" var="color" varStatus="loop">
                 ${color.code}
                 <span class="color-dot" style="background-color: ${color.code}"></span>
                 <c:if test="${!loop.last}">,</c:if>
               </c:forEach>
             </td>
           </tr>
           <tr class="product-table__row">
             <td>Device type</td>
             <td>${phone.deviceType}</td>
           </tr>
           <tr class="product-table__row">
             <td>Bluetooth</td>
             <td>${phone.bluetooth}</td>
           </tr>
         </tbody>
      </table>
    </div>
    <div class="phone-column">
    </div>
    <div class="phone-column">
    </div>
  </div>
  
</tags:master>
