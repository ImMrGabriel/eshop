<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--<%@ page import="com.gabriel.eshop.entity.Product" %>--%>
<html>
<head>
    <title>Product</title>
</head>
<body>
    <a href="./productAll.do">To all products</a>
    <h1>PRODUCT PAGE</h1>
    <%--EL=Expression Languge--%>
    <br/>id: ${product.id}
    <%--Scriptlet--%>
    <%--<br/>id: <%=((Product) request.getAttribute("product")).getId()%>--%>
    <br/>name: ${product.name}
    <br/><%--Add quiz to bucket--%>
        <a href="./productAddToBucket.do?id=${product.id}">Add this product to bucket</a>
    <hr/><%--Show product backet--%>
    <h2>Products in bucket</h2>
    <ul>
        <c:forEach var="productInBucket" items="${productsInBucket}">
            <li>
                <a href="./product.do?id=${productInBucket.key.id}">${productInBucket.key.name}</a>: = ${productInBucket.value}
                (<a href="./productRemoveFromBucket.do?id=${productInBucket.key.id}&redirectToId=${product.id}">X</a>)
            </li>
        </c:forEach>
    </ul>
</body>
</html>
