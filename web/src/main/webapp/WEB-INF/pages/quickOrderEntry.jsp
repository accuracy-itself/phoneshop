<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tags:master pageTitle="Quick order entry">
<div class = "quick_order_body">
    <c:forEach var="success" items="${successes}">
        <div class="success">
            ${success}
        </div>
    </c:forEach>

    <form:form action="quickOrderEntry" method="post" modelAttribute="quickOrderDto">
        <table class="quick_order_table">
             <thead>
                 <tr>
                   <th>Phone Code</th>
                   <th>Quantity</th>
                   <th></th>
                 </tr>
             </thead>
             <tbody>
                 <c:forEach begin="0" end="${quickOrderDto.capacity - 1}" var = "index">
                 <tr>
                     <td>
                        <form:input path="items[${index}].id" value="${items[index].id}"/>
                     </td>
                     <td>
                        <form:input path="items[${index}].quantity" class="quantity" value="${items[index].quantity}"/>
                     <td>
                        <div class="error">
                            <form:errors path="items[${index}].id" class="error"/>
                            <form:errors path="items[${index}].quantity" class="error"/>
                        </div>
                     </td>
                 </tr>
                 </c:forEach>
            </tbody>
        </table>

        <div>
            <input type="submit" value="Add" class="button">
        </div>
    </form:form>
</div>
</tags:master>
