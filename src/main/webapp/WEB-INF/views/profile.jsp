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
                    <h2>My Profile</h2>
                    <%--Sign out--%>
                    <div id="signout">
                        <form action="${contextPath}/logout" method="post">
                            <input type="submit" value="Sign out" class="button gray">
                        </form>
                    </div>
                    <br><br><br>
                    <div class="row">
                        <div class="row profile_pic">
                            <img src="<c:out value='${user.imageUrl}'/>">
                        </div>
                        <div class="row" style="text-align: center;">
                            <c:if test="${message != null}">
                                <p style="color: ${success ? '#20C164' : '#e34949'};"><c:out value="${message}"/></p>
                            </c:if>
                        </div>
                        <section>
                            <div class="vertical-space-30px text-align-center">
                                <div class="no-margin toggle-able">
                                    <form action="#" id="profile_toggle_form" style="display: none;"></form>
                                    <input type="radio" name="toggle" form="profile_toggle_form" value="view_info" id="profile_1" checked="checked">
                                    <label for="profile_1">View info</label>
                                    <input type="radio" name="toggle" form="profile_toggle_form" value="edit_info" id="profile_2">
                                    <label for="profile_2">Edit info</label>
                                    <input type="radio" name="toggle" form="profile_toggle_form" value="edit_password" id="profile_3">
                                    <label for="profile_3">Password</label>
                                </div>
                            </div>

                            <div class="vertical-space-30px"></div>

                            <%-- View info --%>
                            <div id="view_info" class="row toggle-section toggle-initial">
                                <h3><c:out value="${user.fullName}"/></h3>
                                <p><strong class="mint-text">Username:</strong> <c:out value="${user.username}"/></p>
                                <p><strong class="mint-text">Email:</strong> <c:out value="${user.userLogin.email}"/></p>
                                <p><strong class="mint-text">Location:</strong> <c:out value="${user.location}"/></p>
                            </div>

                            <%-- Edit info --%>
                            <div id="edit_info" class="row toggle-section">
                                <form action="${contextPath}/profile/update/info" method="post">

                                    <%--First Name--%>
                                    <label for="f_name">First name:</label>
                                    <input type="text" value="<c:out value="${user.firstName}"/>" id="f_name" name="f_name" class="text_input" placeholder="Ex: John">

                                    <%--Last Name--%>
                                    <label for="l_name">Last name:</label>
                                    <input type="text" value="<c:out value="${user.lastName}"/>" id="l_name" name="l_name" class="text_input" placeholder="Ex: Appleseed">

                                    <%--Username--%>
                                    <label for="username">Username:</label>
                                    <input type="text" value="<c:out value="${user.userLogin.username}"/>" id="username" name="username" class="text_input" placeholder="Ex: jappleseed">

                                    <%--Email--%>
                                    <label for="email">Email:</label>
                                    <input type="email" value="<c:out value="${user.userLogin.email}"/>" id="email" name="email" class="text_input" placeholder="Ex: jappleseed@gmail.com">

                                    <%--Location--%>
                                    <label for="location">Location:</label>
                                    <input type="text" value="<c:out value="${user.location}"/>" id="location" name="location" class="text_input" placeholder="Ex: Charlotte, NC">

                                    <%--Image url--%>
                                    <label for="user-img-url">Image url:</label>
                                    <input type="text" value="<c:out value="${user.imageUrl}"/>" id="user-img-url" name="user-img-url" class="text_input" placeholder="Ex: https://www.website.com/url-to-my-image">

                                    <%--Change password / save changes--%>
                                    <div class="row">
                                        <div class="vertical-align">
                                            <input type="submit" value="Save changes" class="button mint">
                                        </div>
                                    </div>
                                </form>
                            </div>

                            <%-- Edit password --%>
                            <div id="edit_password" class="row toggle-section">
                            <form action="${contextPath}/profile/update/password" method="post">

                                <%--Current password--%>
                                <label for="curr_password">Current password:</label>
                                <input type="password" id="curr_password" name="curr_password" class="text_input" placeholder="Enter current password">

                                <%--New password--%>
                                <label for="new_password">New password:</label>
                                <input type="password" id="new_password" name="new_password" class="text_input" placeholder="Enter new password">

                                <%--Confirm new passwod--%>
                                <label for="conf_new_pass">Confirm new password:</label>
                                <input type="password" id="conf_new_pass" name="conf_new_pass" class="text_input" placeholder="Enter new password again">

                                <%--Change password / save changes--%>
                                <div class="row">
                                    <div class="vertical-align">
                                        <input type="submit" value="Save changes" class="button mint">
                                    </div>
                                </div>
                            </form>
                        </div>
                        </section>
                    </div>
                </section>

                <!-- My Items -->
                <div id="my_items">
                    <section class="section">
<%--                        <h2>My Items</h2>--%>
                        <div class="no-margin text-align-left">
                            <h2 style="display: inline-block;">My Items</h2>
                            <div class="no-margin toggle-able" style="float: right;">
                                <form action="#" id="my_items_toggle_form" style="display: none;"></form>
                                <input type="radio" name="toggle" form="my_items_toggle_form" value="available" id="my_items_1" checked="checked">
                                <label for="my_items_1" class="green-checked">Available</label>
                                <input type="radio" name="toggle" form="my_items_toggle_form" value="unavailable" id="my_items_2" ${show_unavailable ? 'checked' : ''}>
                                <label for="my_items_2" class="red-checked">Unavailable</label>
                                <input type="radio" name="toggle" form="my_items_toggle_form" value="_item" id="my_items_3" ${edit_item or show_add_item ? 'checked' : ''}>
                                <label for="my_items_3" class="blue-checked">${edit_item ? 'Edit' : 'Add new'}</label>
                            </div>
                        </div>

                        <hr class="row-break">
                        <div class="vertical-space-30px"></div>

                        <%--Available items--%>
                        <div id="available" class="row toggle-section ${edit_item == null and show_unavailable == null and show_add_item == null ? 'toggle-initial' : ''}">
                            <c:choose>
                                <c:when test="${empty user.availableItems}">
                                    <p>When you add new items, they will appear here.</p>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${user.availableItems}" var="user_item">
                                        <%--Loop--%>
                                        <div class="section list_item">
                                            <a href="${contextPath}/users/<c:out value='${user.userLogin.username}'/>/items/<c:out value='${user_item.urlValue}'/>">
                                                <div class="no-margin img-wrapper">
                                                    <img src="<c:out value='${user_item.image_url}'/>" class="item_img" style="margin: auto; !important;">
                                                </div>
                                            </a>
                                            <div class="details">
                                                <h3><c:out value="${user_item.name}"/></h3>
                                                <p style="margin-bottom: 3px;"><c:out value="${user_item.categories}"/></p>
                                                <p><small>Condition: <c:out value="${user_item.item_condition}"/></small></p>
                                                <p><small>Posted: <c:out value="${user_item.timeElapsed}"/></small></p>
                                            </div>
                                            <div class="actions">
                                                <form action="" method="post">
                                                    <button type="submit"
                                                            formaction="<c:out value="${contextPath}/users/${user.username}/items/${user_item.urlValue}/edit"/>"
                                                            class="edit">
                                                        <img src="${contextPath}/images/edit.png" alt="Edit" title="Edit details" style="height: 20px !important;">
                                                    </button>
                                                    <button type="submit" id="delete-item-button"
                                                            formaction="<c:out value="${contextPath}/users/${user.username}/items/${user_item.urlValue}/delete"/>"
                                                            class="delete confirm-action">
                                                        <img src="${contextPath}/svg/delete.svg" alt="Delete" title="Remove this item" style="height: 20px !important;">
                                                    </button>
                                                </form>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <%--Unavailable items--%>
                        <div id="unavailable" class="row toggle-section ${show_unavailable != null ? 'toggle-initial' : ''}">
                            <c:choose>
                                <c:when test="${empty user.unavailableItems}">
                                    <p>After you exchange or remove items, they will appear here.</p>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${user.unavailableItems}" var="user_item">
                                        <%--Loop--%>
                                        <div class="section list_item">
                                            <a href="<c:out value="${contextPath}/users/${user.userLogin.username}/items/${user_item.urlValue}"/>">
                                                <div class="no-margin img-wrapper">
                                                    <img src="<c:out value='${user_item.image_url}'/>" class="item_img">
                                                </div>
                                            </a>
                                            <div class="details">
                                                <h3><c:out value="${user_item.name}"/></h3>
                                                <p style="margin-bottom: 3px;"><c:out value="${user_item.categories}"/></p>
                                                <p><small>Condition: <c:out value="${user_item.item_condition}"/></small></p>
                                                <p><small>Posted: <c:out value="${user_item.timeElapsed}"/></small></p>
                                            </div>
                                            <div class="actions">
                                                <form action="#" method="post">
                                                    <c:if test="${user_item.isDeletedByUser()}">
                                                        <button type="submit"
                                                                formaction="<c:out value="${contextPath}/users/${user.username}/items/${user_item.urlValue}/undo-delete"/>"
                                                                class="undo-delete-img button btn-small">
                                                            <img src="${contextPath}/svg/undo.svg" alt="Undo" title="Restore this item" style="height: 20px !important;">
                                                        </button>
                                                        <button type="submit" id="perm-delete-item-button"
                                                                formaction="<c:out value="${contextPath}/users/${user.username}/items/${user_item.urlValue}/perm-delete"/>"
                                                                class="undo-delete-img button btn-small confirm-action">
                                                            <img src="${contextPath}/svg/delete.svg" alt="Undo" title="Permanently delete this item" style="height: 20px !important;">
                                                        </button>
                                                    </c:if>
                                                </form>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <%--Add a new item / edit an existing item--%>
                        <div id="_item" class="row toggle-section text-align-left ${edit_item or show_add_item ? 'toggle-initial' : ''}">
                            <form id="edit-item-form" action="<c:out value="${contextPath}/users/${user.username}/items/${edit_item ? item.urlValue+='/save' : 'add'}"/>" method="post">

                                <c:if test="${item_error != null}">
                                    <p class="red-text" style="margin:0 0 30px;"><c:out value="${item_error}"/></p>
                                </c:if>

                                <%--Name--%>
                                <label for="name">Name of item:</label>
                                <input type="text" value="<c:out value="${item.name}"/>" id="name" name="name" class="text_input" placeholder="Ex: Magical Instruments - MI Guitar">

                                <%--Condition--%>
                                <label>Condition:</label>
                                <select name="condition" style="appearance: none;" class="select-css">
                                    <option value="New" ${item.item_condition.equalsIgnoreCase('new') ? 'selected' : ''}>New</option>
                                    <option value="Like new" ${item.item_condition.equalsIgnoreCase('like new') ? 'selected' : ''}>Like new</option>
                                    <option value="Lightly used" ${item.item_condition.equalsIgnoreCase('lightly used') ? 'selected' : ''}>Lightly used</option>
                                    <option value="Moderately used" ${item.item_condition.equalsIgnoreCase('moderately used') ? 'selected' : ''}>Moderately used</option>
                                    <option value="Heavily used" ${item.item_condition.equalsIgnoreCase('heavily used') ? 'selected' : ''}>Heavily used</option>
                                </select>

                                <%--Categories--%>
                                <label for="categories">Categories:</label>
                                <input type="text" value="<c:out value="${item.categories}"/>" id="categories" name="categories" class="text_input" placeholder="Ex: Music, Technology, Audio">

                                <%--Image url--%>
                                <label for="image_url">Url to image:</label>
                                <input type="text" value="<c:out value="${item.image_url}"/>" id="image_url" name="image_url" class="text_input" placeholder="Ex: https://www.website.com/url-to-my-image">

                                <%--Save--%>
                                <div class="row">
                                    <div class="vertical-align">
                                        <button type="button"  style="float:left;" class="button gray"
                                                onclick="window.location.href='${contextPath}/profile'">
                                            <span>Cancel</span>
                                        </button>
                                        <input type="submit" value="Save" class="button mint">
                                    </div>
                                </div>
                            </form>
                        </div>

                    </section>
                </div>

                <%--Delete account--%>
                <div id="small_sections">
                    <section class="section">
                        <a href="<c:out value="${contextPath}/users/${user.username}"/>" class="blue-text">View public profile</a>
                    </section>
                    <section class="section">
                        <a href="" class="red-text">Delete my account</a>
                    </section>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="../partials/footer.jsp"/>

</body>
</html>


<script>


    // $(document).ready(function() {
    //
    //     // For confirming actions on the site
    //     $(".confirm-action").each(function() {
    //
    //         this.addEventListener("click", function(e) {
    //             if(confirm("Are you sure? This will decline or withdraw all active offers associated with this item.")) {
    //                 return true;
    //             } else {
    //                 e.preventDefault();
    //             }
    //         });
    //     });
    // });


</script>