<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="../partials/head.jsp">
    <jsp:param name="title" value="Register"/>
</jsp:include>
<body>
<div class="container">

    <jsp:include page="../partials/header.jsp"/>
    <jsp:include page="../partials/nav.jsp"/>

    <div class="mid-section">
        <div class="wrapper">
            <section class="section">
                <h2>Register</h2>
                <div class="row">
                    <form action="${contextPath}/register" method="post">

                        <input type="text" name="f_name" class="text_input" placeholder="First name" autofocus>

                        <input type="text" name="l_name" class="text_input" placeholder="Last name">

                        <input type="text" name="username" class="text_input" placeholder="Username">

                        <input type="text" name="email" class="text_input" placeholder="Email">

                        <input type="text" name="location" class="text_input" placeholder="City and state (Ex. Charlotte, NC)">

                        <input type="password" name="password" class="text_input" placeholder="Password">

                        <input type="password" name="conf_pass" class="text_input" placeholder="Confirm password">

                        <div class="row">
                            <div class="vertical-align">
<%--                                <button type="button" formaction="${contextPath}/login" formmethod="get" class="button btn-large gray">Cancel</button>--%>
                                <a href="${contextPath}/login" class="button btn-large gray cancel-btn">Cancel</a>
                                <input type="submit" value="Register" class="button btn-large mint">
                            </div>
                        </div>
                    </form>
                </div>
            </section>
        </div>
        <div class="wrapper">
            <section class="section center-text">
                Don't have an account? <a href="" class="mint-text"><strong>Create one</strong></a>
            </section>
        </div>
    </div>

</div>

<jsp:include page="../partials/footer.jsp"/>

</body>
</html>