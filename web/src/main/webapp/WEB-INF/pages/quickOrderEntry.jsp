<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<tags:master pageTitle="Quick order entry">
<div class="success">
${successes}
</div>
    <form action="quickOrderEntry" method="post">
    <table>
         <thead>
             <tr>
               <th>Phone Code</th>
               <th>Quantity</th>
               <th></th>
             </tr>
         </thead>
         <tbody>
             <c:forEach begin="1" end="${capacity}" var = "index" varStatus="status">
             <tr>
                 <td><input name="phoneCode" value="${codes[status.index - 1]}"/></td>
                 <td><input name="phoneQuantity" class="quantity" value="${quantities[status.index - 1]}"/></td>
                 <td>
                    <div class="error">
                        ${errors[index]}
                    </div>
                 </td>
             </tr>
             </c:forEach>


        </tbody>
    </table>

    <div>
        <input type="submit" value="Add" class="button">
    </div>
    </form>
</tags:master>
