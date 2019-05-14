<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="../partials/head.jsp">
    <jsp:param name="title" value="Home"/>
</jsp:include>
<body>
    <div class="container">

        <jsp:include page="../partials/header.jsp"/>
        <jsp:include page="../partials/nav.jsp"/>
        
        <div class="mid-section">

            <jsp:include page="../partials/filters.jsp"/>

            <main id="main">
                <c:forEach items="${items}" var="item">
                    <c:choose>

                        <%-- Display all items when user is null --%>
                        <c:when test="${user == null}">
                            <c:if test="${item.available == true}">
                                <a href="<c:out value="${contextPath}/users/${item.owner.username}/items/${item.urlValue}"/>">
                                    <figure>
                                        <img src="<c:out value="${item.image_url}"/>" class="prod_img">
                                        <figcaption>
                                            <p class="item_name"><c:out value="${item.name}"/></p>
                                            <p class="item_location"><img src="images/location.svg" class="loc_icon"><c:out value="${item.owner.location}"/></p>
                                        </figcaption>
                                    </figure>
                                </a>
                            </c:if>
                        </c:when>

                        <%-- Display only unowned items when user is not null --%>
                        <c:otherwise>
                            <c:if test="${user.doesNotOwnItem(item) && item.available == true}">
                                <a href="<c:out value="${contextPath}/users/${item.owner.username}/items/${item.urlValue}"/>">
                                    <figure>
                                        <img src="<c:out value="${item.image_url}"/>" class="prod_img">
                                        <figcaption>
                                            <p class="item_name"><c:out value="${item.name}"/></p>
                                            <p class="item_location"><img src="images/location.svg" class="loc_icon"><c:out value="${item.owner.location}"/></p>
                                        </figcaption>
                                    </figure>
                                </a>
                            </c:if>
                        </c:otherwise>

                    </c:choose>
                </c:forEach>
            </main>

        </div>

    </div>

    <jsp:include page="../partials/footer.jsp"/>

</body>
</html>

<script>

var filters = document.getElementsByClassName("filter_name");
var i;

for (i = 0; i < filters.length; i++) {
  filters[i].addEventListener("click", function() {
    var status = this.querySelector(".status");
    status.innerHTML === "+" ? status.innerHTML = "-" : status.innerHTML = "+";
    var content = this.nextElementSibling;
    content.classList.toggle("expanded");
    if (content.style.maxHeight){
      content.style.maxHeight = null;
    } else {
      content.style.maxHeight = content.scrollHeight + "px";
    } 
  });
}

</script>
