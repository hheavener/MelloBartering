<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="../partials/head.jsp">
    <jsp:param name="title" value="Login"/>
</jsp:include>
<body>
    <div class="container">

        <jsp:include page="../partials/header.jsp"/>
        <jsp:include page="../partials/nav.jsp"/>

        <div class="mid-section">
            <div class="wrapper">
                <section class="section">
                    <h2>Login</h2>
                    <c:if test="${message != null}">
                        <p style="color: #e34949;"><c:out value="${message}"/></p>
                    </c:if>
                    <div class="row">
                        <form action="${contextPath}/login" method="post">
                            <label for="user_id">User ID</label>
                            <input type="text" value="<c:out value="${user_id}"/>" id="user_id" name="user_id" class="text_input" placeholder="Username or email" autofocus>
                            <label for="password">Password</label>
                            <input type="password" id="password" name="password" class="text_input" placeholder="Password">
                            <div class="row">
                                <div class="vertical-align">
                                    <a href="" class="gray-text">Forgot password?</a>
                                    <input type="submit" value="Sign in" class="button btn-large mint">
                                </div>
                            </div>
                        </form>
                    </div>
                </section>
            </div>
            <div class="wrapper">
                <section class="section center-text">
                    Don't have an account? <a href="${contextPath}/register" class="mint-text"><strong>Create one</strong></a>
                </section>
            </div>
        </div>

    </div>

    <jsp:include page="../partials/footer.jsp"/>

</body>
</html>