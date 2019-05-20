<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="../partials/head.jsp">
    <jsp:param name="title" value="My Offers"/>
</jsp:include>
<body>
<div class="container">

    <jsp:include page="../partials/header.jsp"/>
    <jsp:include page="../partials/nav.jsp"/>

    <div class="mid-section">
        <section>
            <div class="no-margin text-align-center">
                <div class="vertical-space-30px"></div>
                    <div class="toggle-able" style="margin: auto;">
                        <input type="radio" name="toggle" value="sent" id="toggle1" checked>
                        <label for="toggle1">Sent</label>
                        <input type="radio" name="toggle" value="received" id="toggle2" ${show_received == true ? 'checked' : ''}>
                        <label for="toggle2">Received</label>
                        <input type="radio" name="toggle" value="history" id="toggle3" ${show_history == true ? 'checked' : ''}>
                        <label for="toggle3">History</label>
                    </div>
                <div class="vertical-space-30px"></div>
            </div>

            <!-- Sent -->
            <div id="sent" class="wrapper toggle-section ${show_received == null and show_history == null ? 'toggle-initial' : ''}">
                    <section class="section text-align-center width-1180px">
                        <div class="no-margin text-align-left">
                            <h2>Sent</h2>
                        </div>
                        <hr class="row-break">
                        <c:choose>
                            <c:when test="${empty user.sent}">
                                <div class="row"><p>You have no currently sent offers.</p></div>
                                <div class="vertical-space-30px"></div>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${user.sent}" var="offer" varStatus="loop">
                                <c:if test="${loop.count > 1}"><hr class="row-break"></c:if>
                                <div class="row">
                                    <a href="<c:out value="${contextPath}/users/${offer.sender.username}/items/${offer.senderItem.urlValue}"/>">
                                        <figure>
                                            <img src="<c:out value='${offer.senderItem.image_url}'/>" class="prod_img">
                                            <figcaption>
                                                <p class="item_name"><c:out value="${offer.senderItem.name}"/></p>
                                                <p class="item_location"><img src="images/location.svg" class="loc_icon"><c:out value="${offer.senderItem.owner.location}"/></p>
                                            </figcaption>
                                        </figure>
                                    </a>
                                    <div class="arrow">
                                        <img src="svg/Arrow.svg">
                                    </div>
                                    <a href="<c:out value="${contextPath}/users/${offer.receiver.username}/items/${offer.receiverItem.urlValue}"/>">
                                        <figure>
                                            <img src="<c:out value='${offer.receiverItem.image_url}'/>" class="prod_img">
                                            <figcaption>
                                                <p class="item_name"><c:out value="${offer.receiverItem.name}"/></p>
                                                <p class="item_location"><img src="images/location.svg" class="loc_icon"><c:out value="${offer.receiverItem.owner.location}"/></p>
                                            </figcaption>
                                        </figure>
                                    </a>
                                    <div class="details">
                                        <h3>Details</h3>
                                        <hr>
                                        <h4 style="font-weight: bold;">Sent:</h4>
                                        <p>To:
                                            <a href="${contextPath}/users/<c:out value='${offer.receiver.username}'/>" class="blue-text">
                                                <c:out value="${offer.receiver.fullName}"/>
                                            </a>
                                        </p>
                                        <p><c:out value="${offer.getFormattedDate(offer.offerDate)}"/></p>
                                        <p class="subtext"><c:out value="${offer.getTimeElapsed(offer.offerDate)}"/></p>
                                        <h4>Status:</h4>
                                        <p><c:out value="${offer.moment_viewed == null ? 'Delivered' : 'Viewed by ' += offer.receiver.firstName}"/></p>
                                        <p class="subtext"><c:out value="${offer.getTimeElapsed(offer.moment_viewed)}"/></p>
                                        <form action="#" method="POST">
                                            <button type="submit" id="withdraw-offer-button"
                                                    class="button blue confirm-action"
                                                    formaction="<c:out value="${contextPath}/offers/${offer.id}/withdraw"/>">Withdraw</button>
                                        </form>
                                    </div>
                                </div>
                                <c:if test="${loop.count != user.sent.size()}"><div class="vertical-space-30px"></div></c:if>
                            </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </section>
                </div>

            <!-- Received -->
            <div id="received" class="wrapper toggle-section ${show_received != null ? 'toggle-initial' : ''}">
                    <section class="section text-align-center width-1180px">
                        <div class="no-margin text-align-left">
                            <h2>Received</h2>
                        </div>
                        <hr class="row-break">
                        <c:choose>
                            <c:when test="${empty user.received}">
                                <div class="row"><p>You have no currently sent offers.</p></div>
                                <div class="vertical-space-30px"></div>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${user.received}" var="offer" varStatus="loop">
                                <c:if test="${loop.count > 1}"><hr class="row-break"></c:if>
                                <div class="row">
                                    <a href="<c:out value="${contextPath}/users/${offer.sender.username}/items/${offer.senderItem.urlValue}"/>">
                                        <figure>
                                            <img src="<c:out value="${offer.senderItem.image_url}"/>" class="prod_img">
                                            <figcaption>
                                                <p class="item_name"><c:out value="${offer.senderItem.name}"/></p>
                                                <p class="item_location"><img src="images/location.svg" class="loc_icon"><c:out value="${offer.senderItem.owner.location}"/></p>
                                            </figcaption>
                                        </figure>
                                    </a>
                                    <div class="arrow">
                                        <img src="svg/Arrow.svg">
                                    </div>
                                    <a href="<c:out value="${contextPath}/users/${offer.receiver.username}/items/${offer.receiverItem.urlValue}"/>">
                                        <figure>
                                            <img src="<c:out value="${offer.receiverItem.image_url}"/>" class="prod_img">
                                            <figcaption>
                                                <p class="item_name"><c:out value="${offer.receiverItem.name}"/></p>
                                                <p class="item_location"><img src="images/location.svg" class="loc_icon"><c:out value="${offer.receiverItem.owner.location}"/></p>
                                            </figcaption>
                                        </figure>
                                    </a>
                                    <div class="details">
                                        <h3>Details</h3>
                                        <hr>
                                        <h4 style="font-weight: bold;">Received:</h4>
                                        <p>From:
                                            <a href="<c:out value="${contextPath}/users/${offer.sender.username}"/>" class="blue-text">
                                                <c:out value="${offer.sender.fullName}"/>
                                            </a>
                                        </p>
                                        <p><c:out value="${offer.getFormattedDate(offer.offerDate)}"/></p>
                                        <p class="subtext"><c:out value="${offer.getTimeElapsed(offer.offerDate)}"/></p>
                                        <h4>Status:</h4>
                                        <p>You viewed this:<br><c:out value="${offer.getFormattedDate(offer.moment_viewed)}"/></p>
                                        <form action="#" method="POST">
                                            <button type="submit" id="accept-offer-button"
                                                    class="button green confirm-action"
                                                    formaction="<c:out value="${contextPath}/offers/${offer.id}/accept"/>">Accept</button>
                                            <button type="submit" id="decline-offer-button"
                                                    class="button red m-l-25 confirm-action"
                                                    formaction="<c:out value="${contextPath}/offers/${offer.id}/decline"/>">Decline</button>
                                        </form>
                                    </div>
                                </div>
                                <c:if test="${loop.count != user.received.size()}"><div class="vertical-space-30px"></div></c:if>
                            </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </section>
                </div>

            <!-- History -->
            <div id="history" class="wrapper toggle-section ${show_history != null ? 'toggle-initial' : ''}">
                    <section class="section text-align-center width-1180px">

                            <%-- Toggle buttons --%>
                        <div class="no-margin text-align-left">
                            <h2 style="display: inline-block; margin: 0;">History</h2>
                            <div class="no-margin toggle-able" style="float: right;">
                                <input type="radio" name="hist-toggle" value="accepted" id="history1" checked>
                                <label for="history1" class="green-checked">Accepted</label>
                                <input type="radio" name="hist-toggle" value="rejected" id="history2" ${show_rejected == true ? 'checked' : ''}>
                                <label for="history2" class="red-checked">Declined</label>
                                <input type="radio" name="hist-toggle" value="withdrawn" id="history3" ${show_withdrawn == true ? 'checked' : ''}>
                                <label for="history3" class="blue-checked">Withdrawn</label>
                            </div>
                        </div>

                            <%-- Accepted offers --%>
                        <div id="accepted" class="toggle-section ${show_rejected == null and show_withdrawn == null ? 'toggle-initial' : ''}">
                            <hr class="row-break">
                            <div class="vertical-space-30px"></div>
                            <c:choose>
                                <c:when test="${empty accepted}">
                                    <div class="row"><p>None of your offers have been accepted.</p></div>
                                    <div class="vertical-space-30px"></div>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${accepted}" var="offer" varStatus="loop">
                                        <div class="row">

                                                <%-- Sender item --%>
                                            <a href="<c:out value="${contextPath}/users/${offer.sender.username}/items/${offer.senderItem.urlValue}"/>">
                                                <figure>
                                                    <img src="<c:out value="${offer.senderItem.image_url}"/>" class="prod_img">
                                                    <figcaption>
                                                        <p class="item_name"><c:out value="${offer.senderItem.name}"/></p>
                                                        <p class="item_location"><img src="images/location.svg" class="loc_icon"><c:out value="${offer.senderItem.owner.location}"/></p>
                                                    </figcaption>
                                                </figure>
                                            </a>

                                                <%-- Arrow --%>
                                            <div class="arrow">
                                                <img src="svg/Arrow.svg">
                                            </div>

                                                <%-- Receiver item --%>
                                            <a href="<c:out value="${contextPath}/users/${offer.receiver.username}/items/${offer.receiverItem.urlValue}"/>">
                                                <figure>
                                                    <img src="<c:out value="${offer.receiverItem.image_url}"/>" class="prod_img">
                                                    <figcaption>
                                                        <p class="item_name"><c:out value="${offer.receiverItem.name}"/></p>
                                                        <p class="item_location"><img src="images/location.svg" class="loc_icon"><c:out value="${offer.receiverItem.owner.location}"/></p>
                                                    </figcaption>
                                                </figure>
                                            </a>

                                                <%-- Details --%>
                                            <div class="details">
                                                <h3>Details</h3>
                                                <hr>
                                                    <%-- Sent / Received --%>
                                                <h4 style="font-weight: bold;"><c:out value="${user.sentOffer(offer.id) ? 'Sent:' : 'Received:'}"/></h4>
                                                    <%-- Sent to / Sent by --%>
                                                <p class="subtext" style="color: #4c4c4c;">
                                                    <c:choose>
                                                        <c:when test="${user.receivedOffer(offer.id)}">
                                                            From:
                                                            <a href="<c:out value="${contextPath}/users/${offer.sender.username}"/>" class="subtext link-dark">
                                                                <c:out value="${offer.sender.fullName}"/>
                                                            </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            To:
                                                            <a href="<c:out value="${contextPath}/users/${offer.receiver.username}"/>" class="subtext link-dark">
                                                                <c:out value="${offer.receiver.fullName}"/>
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </p>
                                                    <%-- Date offered --%>
                                                <p class="subtext" style="color: #4c4c4c;"><c:out value="${offer.getFormattedDate(offer.offerDate)}"/></p>
                                                    <%-- Time since offer --%>
                                                <p class="subtext"><c:out value="${offer.getTimeElapsed(offer.offerDate)}"/></p>

                                                <h4>Status:</h4>
                                                <p class="subtext" style="color:#20C164;">
                                                    Accepted <c:out value="${user.receivedOffer(offer.id) ? ' by you' : ' by ' += offer.receiver.firstName}"/>
                                                </p>
                                                    <%-- Date of action --%>
                                                <p class="subtext" style="color:#4c4c4c;"><c:out value="${offer.getFormattedDate(offer.offerClosedDate)}"/></p>
                                                    <%-- Time since action --%>
                                                <p class="subtext"><c:out value="${offer.getTimeElapsed(offer.offerClosedDate)}"/></p>
                                            </div>
                                        </div><br><br>
                                        <c:if test="${loop.count != accepted.size()}"><div class="vertical-space-30px"></div></c:if>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </div>

                            <%-- Rejected offers --%>
                        <div id="rejected" class="toggle-section ${show_rejected != null ? 'toggle-initial' : ''}">
                            <hr class="row-break">
                            <div class="vertical-space-30px"></div>
                            <c:choose>
                                <c:when test="${empty rejected}">
                                    <div class="row"><p>None of your offers have been declined.</p></div>
                                    <div class="vertical-space-30px"></div>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${rejected}" var="offer" varStatus="loop">
                                        <div class="row">

                                                <%-- Sender item --%>
                                            <a href="<c:out value="${contextPath}/users/${offer.sender.username}/items/${offer.senderItem.urlValue}"/>">
                                                <figure>
                                                    <img src="<c:out value="${offer.senderItem.image_url}"/>" class="prod_img">
                                                    <figcaption>
                                                        <p class="item_name"><c:out value="${offer.senderItem.name}"/></p>
                                                        <p class="item_location"><img src="images/location.svg" class="loc_icon"><c:out value="${offer.senderItem.owner.location}"/></p>
                                                    </figcaption>
                                                </figure>
                                            </a>

                                                <%-- Arrow --%>
                                            <div class="arrow">
                                                <img src="svg/Arrow.svg">
                                            </div>

                                                <%-- Receiver item --%>
                                            <a href="<c:out value="${contextPath}/users/${offer.receiver.username}/items/${offer.receiverItem.urlValue}"/>">
                                                <figure>
                                                    <img src="<c:out value="${offer.receiverItem.image_url}"/>" class="prod_img">
                                                    <figcaption>
                                                        <p class="item_name"><c:out value="${offer.receiverItem.name}"/></p>
                                                        <p class="item_location"><img src="images/location.svg" class="loc_icon"><c:out value="${offer.receiverItem.owner.location}"/></p>
                                                    </figcaption>
                                                </figure>
                                            </a>

                                                <%-- Details --%>
                                            <div class="details">
                                                <h3>Details</h3>
                                                <hr>
                                                    <%-- Sent / Received --%>
                                                <h4 style="font-weight: bold;"><c:out value="${user.sentOffer(offer.id) ? 'Sent:' : 'Received:'}"/></h4>
                                                    <%-- Sent to / Sent by --%>
                                                <p class="subtext" style="color: #4c4c4c;">
                                                    <c:choose>
                                                        <c:when test="${user.receivedOffer(offer.id)}">
                                                            From:
                                                            <a href="<c:out value="${contextPath}/users/${offer.sender.username}"/>" class="subtext link-dark">
                                                                <c:out value="${offer.sender.fullName}"/>
                                                            </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            To:
                                                            <a href="<c:out value="${contextPath}/users/${offer.receiver.username}"/>" class="subtext link-dark">
                                                                <c:out value="${offer.receiver.fullName}"/>
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </p>
                                                    <%-- Date offered --%>
                                                <p class="subtext" style="color: #4c4c4c;"><c:out value="${offer.getFormattedDate(offer.offerDate)}"/></p>
                                                    <%-- Time since offer --%>
                                                <p class="subtext"><c:out value="${offer.getTimeElapsed(offer.offerDate)}"/></p>

                                                <h4>Status:</h4>
                                                <p class="subtext undo" style="color:#e34949;">
                                                    Declined <c:out value="${user.receivedOffer(offer.id) ? ' by you' : ' by ' += offer.receiver.firstName}"/>
                                                    <c:if test="${user.receivedOffer(offer.id) and offer.actionCanBeUndone()}">
                                                            <span class="subtext">
                                                                <button type="submit"
                                                                        form="undo-delete"
                                                                        formmethod="post"
                                                                        formaction="<c:out value="${contextPath}/offers/${offer.id}/undo/decline"/>"
                                                                        class="button btn-small red">Undo</button>
                                                            </span>
                                                    </c:if>
                                                </p>
                                                <form id="undo-delete" action="#" style="display:none;"></form>
                                                    <%-- Date of action --%>
                                                <p class="subtext" style="color:#4c4c4c;"><c:out value="${offer.getFormattedDate(offer.offerClosedDate)}"/></p>
                                                    <%-- Time since action --%>
                                                <p class="subtext"><c:out value="${offer.getTimeElapsed(offer.offerClosedDate)}"/></p>
                                            </div>
                                        </div><br><br>
                                        <c:if test="${loop.count != rejected.size()}"><div class="vertical-space-30px"></div></c:if>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>

                        </div>

                            <%-- Withdrawn offers --%>
                        <div id="withdrawn" class="toggle-section ${show_withdrawn != null ? 'toggle-initial' : ''}">
                            <hr class="row-break">
                            <div class="vertical-space-30px"></div>
                            <c:choose>
                                <c:when test="${empty withdrawn}">
                                    <div class="row"><p>None of your offers have been withdrawn.</p></div>
                                    <div class="vertical-space-30px"></div>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${withdrawn}" var="offer" varStatus="loop">
                                        <div class="row">
                                                <%-- Sender item --%>
                                            <a href="<c:out value="${contextPath}/users/${offer.sender.username}/items/${offer.senderItem.urlValue}"/>">
                                                <figure>
                                                    <img src="<c:out value="${offer.senderItem.image_url}"/>" class="prod_img">
                                                    <figcaption>
                                                        <p class="item_name"><c:out value="${offer.senderItem.name}"/></p>
                                                        <p class="item_location"><img src="images/location.svg" class="loc_icon"><c:out value="${offer.senderItem.owner.location}"/></p>
                                                    </figcaption>
                                                </figure>
                                            </a>

                                                <%-- Arrow --%>
                                            <div class="arrow">
                                                <img src="svg/Arrow.svg">
                                            </div>

                                                <%-- Receiver item --%>
                                            <a href="<c:out value="${contextPath}/users/${offer.receiver.username}/items/${offer.receiverItem.urlValue}"/>">
                                                <figure>
                                                    <img src="<c:out value="${offer.receiverItem.image_url}"/>" class="prod_img">
                                                    <figcaption>
                                                        <p class="item_name"><c:out value="${offer.receiverItem.name}"/></p>
                                                        <p class="item_location"><img src="images/location.svg" class="loc_icon"><c:out value="${offer.receiverItem.owner.location}"/></p>
                                                    </figcaption>
                                                </figure>
                                            </a>

                                                <%-- Details --%>
                                            <div class="details">
                                                <h3>Details</h3>
                                                <hr>
                                                    <%-- Sent / Received --%>
                                                <h4 style="font-weight: bold;"><c:out value="${user.sentOffer(offer.id) ? 'Sent:' : 'Received:'}"/></h4>
                                                    <%-- Sent to / Sent by --%>
                                                <p class="subtext" style="color: #4c4c4c;">
                                                    <c:choose>
                                                        <c:when test="${user.receivedOffer(offer.id)}">
                                                            From:
                                                            <a href="<c:out value="${contextPath}/users/${offer.sender.username}"/>" class="subtext link-dark">
                                                                <c:out value="${offer.sender.fullName}"/>
                                                            </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            To:
                                                            <a href="<c:out value="${contextPath}/users/${offer.receiver.username}"/>" class="subtext link-dark">
                                                                <c:out value="${offer.receiver.fullName}"/>
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </p>
                                                    <%-- Date offered --%>
                                                <p class="subtext" style="color: #4c4c4c;"><c:out value="${offer.getFormattedDate(offer.offerDate)}"/></p>
                                                    <%-- Time since offer --%>
                                                <p class="subtext"><c:out value="${offer.getTimeElapsed(offer.offerDate)}"/></p>

                                                <h4>Status:</h4>
                                                <p class="subtext undo" style="color:#49BAE3;">
                                                    Withdrawn <c:out value="${user.sentOffer(offer.id) ? ' by you' : ' by ' += offer.sender.firstName}"/>
                                                    <c:if test="${user.sentOffer(offer.id) and offer.actionCanBeUndone()}">
                                                            <span class="subtext">
                                                                <button type="submit"
                                                                        form="undo-withdraw"
                                                                        formmethod="post"
                                                                        formaction="<c:out value="${contextPath}/offers/${offer.id}/undo/withdraw"/>"
                                                                        class="button btn-small blue">Undo</button>
                                                            </span>
                                                    </c:if>
                                                </p>
                                                <form id="undo-withdraw" action="#" style="display:none;"></form>
                                                    <%-- Date of action --%>
                                                <p class="subtext" style="color:#4c4c4c;"><c:out value="${offer.getFormattedDate(offer.offerClosedDate)}"/></p>
                                                    <%-- Time since action --%>
                                                <p class="subtext"><c:out value="${offer.getTimeElapsed(offer.offerClosedDate)}"/></p>
                                            </div>
                                        </div>
                                        <c:if test="${loop.count != withdrawn.size()}"><div class="vertical-space-30px"></div></c:if>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </div>

                    </section>
                </div>

        </section>
    </div>

</div>

<jsp:include page="../partials/footer.jsp"/>

</body>
</html>


<script>



</script>