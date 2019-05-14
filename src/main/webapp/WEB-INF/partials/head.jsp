<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${param.title}</title>
    <link rel="icon" href="${contextPath}/svg/favicon.png" type="image/png" sizes="32x32">
    <link rel="stylesheet" href="${contextPath}/styles/main.css">
    <link rel="stylesheet" href="${contextPath}/styles/list-item.css">
    <link rel="stylesheet" href="${contextPath}/styles/figure.css">
    <link rel="stylesheet" href="${contextPath}/styles/form-styles.css">
    <link rel="stylesheet" href="${contextPath}/styles/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <c:if test="${pageContext.request.requestURI.contains('index')}">
        <link rel="stylesheet" href="${contextPath}/styles/index.css">
    </c:if>
    <c:if test="${pageContext.request.requestURI.contains('item')}">
        <link rel="stylesheet" href="${contextPath}/styles/item.css">
        <link rel="stylesheet" href="${contextPath}/styles/checkbox.css">
    </c:if>
    <c:if test="${pageContext.request.requestURI.contains('profile')}">
        <link rel="stylesheet" href="${contextPath}/styles/profile.css">
    </c:if>
    <c:if test="${pageContext.request.requestURI.contains('user')}">
        <link rel="stylesheet" href="${contextPath}/styles/profile.css">
        <link rel="stylesheet" href="${contextPath}/styles/user.css">
    </c:if>
    <c:if test="${pageContext.request.requestURI.contains('offers')}">
        <link rel="stylesheet" href="${contextPath}/styles/myoffers.css">
    </c:if>
    <c:if test="${pageContext.request.requestURI.contains('login') or pageContext.request.requestURI.contains('registration')}">
        <link rel="stylesheet" href="${contextPath}/styles/login.css">
    </c:if>

</head>