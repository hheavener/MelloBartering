<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<footer id="footer">
    <div class="links-container">
        <div class="center">
            <div class="links block">
                <h2>About</h2>
                <a href="">About us</a>
                <a href="">Our story</a>
                <a href="">Feature ideas</a>
                <a href="">Why Mello?</a>
            </div>
            <div class="links block">
                <h2>Community</h2>
                <a href="">Testimonials</a>
                <a href="">Blog</a>
                <a href="">Events</a>
            </div>
            <div class="links block">
                <h2>Help</h2>
                <a href="">Contact us</a>
                <a href="">Report an issue</a>
                <a href="">Tutorials</a>
                <a href="">Forgot password?</a>
            </div>
            <div class="links">
                <h2>Connect</h2>
                <a href=""><img src="${contextPath}/images/social_media_icons/FB_Logo.png"></a>
                <a href=""><img src="${contextPath}/images/social_media_icons/Insta_Logo.png"></a>
                <a href=""><img src="${contextPath}/images/social_media_icons/Twitter_Logo.png"></a>
                <a href=""><img src="${contextPath}/images/social_media_icons/Email_Logo.png"></a>
            </div>
        </div>
    </div>
    <p>Copyright &copy; Mello 2019. All Rights Reserved.</p>
    <c:if test="${pageContext.request.requestURI.contains('profile') or pageContext.request.requestURI.contains('offers')}">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    </c:if>
    <script src="${contextPath}/scripts/sticky-nav.js"></script>
    <script src="${contextPath}/scripts/toggle-able.js"></script>
</footer>