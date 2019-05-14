<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div id="nav">
    <nav>
        <a href="${contextPath}/" class="left ${pageContext.request.requestURI.contains('index') ? 'active' : ''}">All listings</a>
        <c:choose>
            <c:when test="${user != null}">
<%--                <a href="${contextPath}/recommended" class="left ${recommended == true ? 'active' : ''}">Recommended</a>--%>
                <a href="${contextPath}/profile" class="right ${pageContext.request.requestURI.contains('profile') ? 'active' : ''}">My Profile</a>
                <a href="${contextPath}/offers" class="right ${pageContext.request.requestURI.contains('offers') ? 'active' : ''}">My Offers</a>
            </c:when>
            <c:otherwise>
                <a href="${contextPath}/login" class="right ${pageContext.request.requestURI.contains('login') ? 'active' : ''}">Login</a>
            </c:otherwise>
        </c:choose>

        <form action="${contextPath}/search" id="search-form">
            <input type="text" id="search-bar" name="search" placeholder="Search...">
            <button type="submit"><i class="fa fa-search"></i></button>
        </form>

    </nav>
</div>



<style>

    #nav {
        position: relative;
    }

    #search-form {
        width: 40%;
        margin: auto;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
    }

    #search-bar {
        width: 100%;
        height: 60%;
        box-sizing: border-box;
        vertical-align: middle;
        margin: 10px 0;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        border-radius: 50px;
        border: none;
        padding-left: 15px;
        background: #333;
        outline: none;
        color: white;
        z-index: 1;
    }

    #search-bar::placeholder { /* Chrome, Firefox, Opera, Safari 10.1+ */
        color: #898989;
        opacity: 1; /* Firefox */
    }

    #search-bar::-ms-input-placeholder { /* Internet Explorer 10-11 */
        color: #898989;
    }

    #search-bar::-ms-input-placeholder { /* Microsoft Edge */
        color: #898989;
    }

    #search-form button {
        border: none;
        background: #B7B7B7;
        outline: none;
        cursor: pointer;
        border-radius: 0 30px 30px 0 ;
        margin: 10px 0;
        padding: 0 12px 2px;
        position: absolute;
        top: 0;
        bottom: 0;
        right: 0;
        z-index: 2;
        background: linear-gradient(180deg, rgba(179,179,179,1) 0%, rgba(66,66,66,1) 200%);
    }

    .fa-search:before {
        padding-right: 2px;
    }

</style>