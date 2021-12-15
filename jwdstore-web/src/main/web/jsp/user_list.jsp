<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/download/" var="imgPath"/>
<c:url value="/controller?command=update_user_role" var="updateUserRoleCommand"/>
<c:url value="/controller?command=ban_user" var="banUserCommand"/>
<c:url value="/controller?command=unblock_user" var="unblockUserCommand"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>

<!DOCTYPE HTML>
<html>
<head>
    <title><fmt:message key="user.management"/></title>
    <c:import url="header.jsp"/>
    <main>
        <c:if test="${isRoleUpdated == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="positive.role.updating.message"/></strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("isRoleUpdated");%>
        </c:if>
        <c:if test="${isUserBaned == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="positive.ban.result.message"/></strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("isUserBaned");%>
        </c:if>
        <c:if test="${isUserUnblocked == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="positive.unblock.result.message"/></strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("isUserUnblocked");%>
        </c:if>
        <c:if test="${isRoleUpdated == false || isUserBaned == false || isUserUnblocked == false}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="something.went.wrong"/> </strong> <fmt:message key="try.again"/>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("isRoleUpdated");%>
            <%request.getSession().removeAttribute("isUserUnblocked");%>
            <%request.getSession().removeAttribute("isUserBaned");%>
        </c:if>
        <div class="container-fluid name">
            <p class="h2 bold"><fmt:message key="user.management"/> </p>
        </div>
        <c:if test="${empty userList}">
            <div class="container-fluid name">
                <p class="h4 bold"><fmt:message key="users.not.found"/> </p>
            </div>
        </c:if>
        <c:if test="${!empty userList}">
            <c:forEach items="${userList}" var="user">
                <div class="container-fluid">
                    <div class="card user-card">
                        <div class="card-header order-info">
                            <c:out value="${user.login}"/>
                        </div>
                        <div class="card-body user-list-info">
                            <div class="row">
                                <div class="col-sm-1">
                                    <img src="${imgPath}users_${user.profilePictureName}"
                                         alt="Фото пользователя" class="img-fluid user-picture"/>
                                    <c:if test="${user.role.name != 'banned'}">
                                        <form action="${banUserCommand}" method="post">
                                            <input type="hidden" value="${user.id}" name="userId">
                                            <button type="submit" class="btn ban-button"><fmt:message key="block"/> </button>
                                        </form>
                                    </c:if>
                                    <c:if test="${user.role.name == 'banned'}">
                                        <form action="${unblockUserCommand}" method="post">
                                            <input type="hidden" value="${user.id}" name="userId">
                                            <button type="submit" class="btn unblock-button"><fmt:message key="unblock"/> </button>
                                        </form>
                                    </c:if>
                                </div>
                                <div class="col-xl-11">
                                    <form method="post" action="${updateUserRoleCommand}">
                                        <c:if test="${user.role.name == 'banned'}">
                                            <span class="bold ban-message h4">Заблокирован</span>
                                        </c:if> <br/>
                                        <span class="bold">ID:</span> <c:out value="${user.id}"/>
                                        <br/><span class="bold">Email:</span> <c:out value="${user.email}"/>
                                        <br/><span class="bold"><fmt:message key="name"/>:</span> <c:out value="${user.name}"/> <c:out
                                            value="${user.surname}"/>
                                        <br/><span class="bold"><fmt:message key="birth.date"/>:</span> <c:out value="${user.birthDate}"/>
                                        <br/>
                                        <c:if test="${user.role.name != 'banned'}">
                                            <div class="row change-role-form">
                                                <span class="status-message"><fmt:message key="role"/> :</span>
                                                <div class="col-lg-1">
                                                    <select class="custom-select" name="roleNumber">
                                                        <c:forEach var="role" items="${roles}">
                                                            <option value="${role.number}" ${user.role == role ? 'selected' : ''}>${role.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <input type="hidden" value="${user.id}" name="userId">
                                                <button class="btn btn-outline-secondary" type="submit"
                                                        id="button-addon2"><fmt:message
                                                        key="change"/></button>
                                            </div>
                                        </c:if>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </main>
<c:import url="footer.jsp"/>