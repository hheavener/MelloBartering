<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="../partials/head.jsp">
    <jsp:param name="title" value="${item.name}"/>
</jsp:include>
<body>
    <div class="container">

        <jsp:include page="../partials/header.jsp"/>
        <jsp:include page="../partials/nav.jsp"/>

        <!-- Sent -->
        <div class="mid-section">
            <div class="wrapper">
                <section class="section">
                    <a href="${contextPath}/" class="back_button"><img src="${contextPath}/images/back_arrow.svg"></a>
                    <h2 class="prod_title"><c:out value="${item.name}"/></h2>
                    <div class="row">
                        <figure class="no_hover_effect">
                            <img src="<c:out value='${item.image_url}'/>" class="prod_img">
                            <figcaption>
                                <p class="item_name"><c:out value="${item.name}"/></p>
                                <p class="item_location"><img src="${contextPath}/images/location.svg" class="loc_icon"><c:out value="${item.owner.location}"/></p>
                            </figcaption>
                        </figure>
                        <div class="details">
                            <h3>Details</h3>
                            <hr>
                            <h4>Owner:</h4><p><c:out value="${item.owner.fullName}"/></p><br>
                            <h4>Posted:</h4><p><c:out value="${item.timeElapsed}"/></p><br>
                            <h4>Condition:</h4><p><c:out value="${item.item_condition}"/></p><br>
                            <h4>Categories:</h4><p><c:out value="${item.categories}"/></p><br>
                            <c:if test="${item.available == false}">
                                <p style="color: #e34949; margin-top: 20px;">Item no longer available</p>
                            </c:if>
                            <form id="item-form" action="${contextPath}/users/${user.username}/items/${item.urlValue}/edit" method="POST" style="${item.available == true ? 'margin-top:70px;' : ''}">
                                <c:if test="${user.ownsItem(item)}">
                                    <input type="submit" value="Edit details" class="button blue" style="margin-right: 30px;">
                                </c:if>
                                <button type="button"
                                        class="button gray"
                                        style="margin-left: 0 !important;"
                                        onclick="window.location.href='${contextPath}/users/${item.owner.username}'">
                                    <span>More from this user</span>
                                </button>
                            </form>
                        </div>
                    </div>
                </section>

                <!-- Make an offer -->
                <c:if test="${!user.ownsItem(item)}">
                    <div id="my_items">
                        <section class="section">
                            <c:choose>
                                <c:when test="${user == null}">
                                    <p><a href="${contextPath}/login" class="mint-text"><strong>Sign in</strong></a> to make an offer.</p>
                                </c:when>
                                <c:when test="${empty user.availableItems}">
                                    <p><a href="${contextPath}/users/${user.username}/items/new" class="blue-text">Add an item</a> to make an offer.</p>
                                </c:when>
                                <c:when test="${item.available == false}">
                                    <p>Offer not allowed.</p>
                                </c:when>
                                <c:otherwise>
                                    <h2>Make an offer</h2>
                                    <c:out value="${message}"/>
                                    <form action="${contextPath}/offers/new" method="POST">
                                        <c:forEach items="${user.availableItems}" var="user_item" varStatus="loop">
                                            <label for="${user_item.id}" class="pointer">
                                                <div class="section list_item">
                                                    <div class="no-margin img-wrapper">
                                                        <img src="<c:out value='${user_item.image_url}'/>" class="item_img">
                                                    </div>
                                                    <div class="details">
                                                        <h3><c:out value="${user_item.name}"/></h3>
                                                        <p style="margin-bottom: 3px;"><c:out value="${user_item.categories}"/></p>
                                                        <p><small>Condition: <c:out value="${user_item.item_condition}"/></small></p>
                                                        <p><small>Posted: <c:out value="${user_item.timeElapsed}"/></small></p>
                                                    </div>
                                                    <div class="actions">
                                                        <c:choose>
                                                            <c:when test="${user.doesNotHaveOfferWith(user_item.id, item.id)}">
                                                                <input type="radio"
                                                                       name="sender_item"
                                                                       id="${user_item.id}"
                                                                       value="${user_item.id}"
                                                                       class="checkbox" ${loop.count == 1 ? 'checked' : ''}>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span>Already<br>offered</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </div>
                                            </label>
                                        </c:forEach>
                                        <input type="hidden" name="receiver_id" value="<c:out value='${item.owner.id}'/>">
                                        <input type="hidden" name="receiver_item" value="<c:out value='${item.id}'/>">
                                        <input type="submit" value="Confirm" class="button mint" id="confirm_btn">
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </section>
                    </div>
                </c:if>
            </div>
        </div>

    </div>

    <jsp:include page="../partials/footer.jsp"/>

</body>
</html>

<script>

    $('.checkbox').change(function() {
        if ($('.checkbox:checked').length) {
            $('#confirm_btn').removeAttr('disabled');
        } else {
            $('#confirm_btn').attr('disabled', 'disabled');
        }
    });

</script>