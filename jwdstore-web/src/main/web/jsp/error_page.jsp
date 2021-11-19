<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%request.setAttribute("catalogURL", request.getContextPath() + "/controller?command=catalog");%>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>

<!DOCTYPE HTML>
<html>
<head>
    <title><fmt:message key="error"/></title>
    <c:import url="header.jsp"/>
    <main>
        <div class="container-fluid">
            <div class="row">
                <div class="col-xl-4"></div>
                <div class="col-xl-4">
                    <div class="card text-center registration-form-idents">
                        <div class="card-header authorization bold">
                            <fmt:message key="something.went.wrong"/>
                        </div>
                        <div class="card-body authorization">
                            <fmt:message key="return.to.catalog"/>
                            <div class="row">
                                <div class="col-xl-4"></div>
                                <div class="col-xl-4">
                                    <a href="${catalogURL}">
                                        <button class="my-button"><fmt:message key="catalog"/></button>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
<c:import url="footer.jsp"/>