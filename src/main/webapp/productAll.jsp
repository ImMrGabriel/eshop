<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Products</title>
</head>
<body>
    <h1>ALL PRODUCTS PAGE</h1>
    <ul>
        <c:forEach var="product" items="${productList}">
            <li>
                <a href="./product.do?id=${product.id}">${product.name}</a>
            </li>
        </c:forEach>
    </ul>
</body>
</html>

