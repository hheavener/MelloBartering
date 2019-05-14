<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="../partials/head.jsp">
    <jsp:param name="title" value="My Profile"/>
</jsp:include>
<body>
<div class="container">

    <jsp:include page="../partials/header.jsp"/>
    <jsp:include page="../partials/nav.jsp"/>

    <div class="mid-section">
        <div class="wrapper">
            <section class="section" id="profile">
                <div class="row">
                    <div class="row profile_pic">
                        <img src="<c:out value='${req_user.imageUrl}'/>">
                    </div>
                    <div class="row">
                        <h2><c:out value="${req_user.fullName}"/></h2>
                        <p><c:out value="${req_user.location}"/></p>
                    </div>
                </div>
            </section>

            <!-- My Items -->
            <div id="my_items">
                <section class="section">
                    <h2><c:out value="${req_user.firstName}'s"/> Items</h2>

                    <c:forEach items="${req_user.availableItems}" var="user_item">
                        <%--Loop--%>
                        <a href="${contextPath}/users/<c:out value='${req_user.userLogin.username}'/>/items/<c:out value='${user_item.urlValue}'/>">
                            <div class="section list_item">
                                <div class="no-margin img-wrapper">
                                    <img src="<c:out value='${user_item.image_url}'/>" class="item_img">
                                </div>
                                <div class="details" style="max-width: 525px !important;">
                                    <h3><c:out value="${user_item.name}"/></h3>
                                    <p style="margin-bottom: 3px;"><c:out value="${user_item.categories}"/></p>
                                    <p><small>Condition: <c:out value="${user_item.item_condition}"/></small></p>
                                    <p><small>Posted: <c:out value="${user_item.timeElapsed}"/></small></p>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                </section>
            </div>

            <%--Delete account--%>
            <div id="small_sections">
                <section class="section">
                    <c:choose>
                        <c:when test="${req_user.username == user.username}">
                            <a href="${contextPath}/profile" class="blue-text">Return to profile</a>
                        </c:when>
                        <c:otherwise>
                            <a href="" class="blue-text">Leave feedback for this user</a>
                        </c:otherwise>
                    </c:choose>
                </section>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../partials/footer.jsp"/>
</body>
</html>