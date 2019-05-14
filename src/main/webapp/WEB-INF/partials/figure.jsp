<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<figure class="no_hover_effect">
    <img src="${contextPath}/images/<c:out value='${param.imageUrl}'/>" class="prod_img">
    <figcaption>
        <p class="item_name"><c:out value="${param.item_name}"/></p>
        <p class="item_location"><img src="${contextPath}/images/location.svg" class="loc_icon"><c:out value="${param.location}"/></p>
    </figcaption>
</figure>
<div class="details">
    <h3>Details</h3>
    <hr>
    <h4>Owner:</h4><p><c:out value="${param.ownerFullName}"/></p><br>
    <h4>Posted:</h4><p><c:out value="${param.timeElapsed}"/></p><br>
    <h4>Condition:</h4><p><c:out value="${param.condition}"/></p><br>
    <h4>Categories:</h4><p><c:out value="${param.categories}"/></p>
    <form action="" method="POST">
        <input type="submit" value="Add to watchlist" class="button blue">
        <button type="button" class="button gray" onclick="window.location.href='${contextPath}/users/${param.ownerUsername}'">
            More from this user
        </button>
    </form>
</div>

<jsp:include page="../partials/figure.jsp">
    <jsp:param name="item_name" value="${item.name}"/>
    <jsp:param name="imageUrl" value="${item.image_url}"/>
    <jsp:param name="location" value="${item.owner.location}"/>
    <jsp:param name="ownerFullName" value="${item.owner.fullName}"/>
    <jsp:param name="ownerUsername" value="${item.owner.username}"/>
    <jsp:param name="timeElapsed" value="${item.timeElapsed}"/>
    <jsp:param name="condition" value="${item.item_condition}"/>
    <jsp:param name="categories" value="${item.categories}"/>
</jsp:include>