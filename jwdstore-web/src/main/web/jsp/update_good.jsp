<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>

<c:url value="/controller?command=update_good" var="updateGoodCommand"/>

<!DOCTYPE HTML>
<html>
<head>
    <title><fmt:message key="good.changing"/> </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <c:import url="header.jsp"/>
    <main>
        <div class="container-fluid">
            <div class="row">
                <div class="col-xl-4"></div>
                <div class="col-xl-4">
                    <div class="card text-center registration-form-idents">
                        <div class="card-header authorization bold">
                            <fmt:message key="good.changing"/>
                        </div>
                        <div class="card-body authorization">
                            <form action="${updateGoodCommand}" id="good-inputs" method="post" class="reg-inputs"
                                  enctype="multipart/form-data">
                                <div class="row row-in-registration">
                                    <div class="col-xl-6">
                                        <div class="form-group">
                                            <label for="name"><fmt:message key="title"/> </label>
                                            <input type="text" value="<c:out value="${good.name}"/>" class="form-control" id="name" name="name">
                                        </div>
                                    </div>
                                    <div class="col-xl-6">
                                        <div class="form-group">
                                            <label for="price"><fmt:message key="price"/> </label>
                                            <input type="number" step="0.01" min="1" max="1000000" placeholder="0,00"
                                                   class="form-control" value="<c:out value="${good.price}"/>" id="price" name="price">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="description"><fmt:message key="description"/> </label>
                                    <textarea name="description" id="description"
                                              class="form-control review-area" aria-label="With textarea"
                                              placeholder="Описание" maxlength="400"><c:out value="${good.description}"/></textarea>
                                </div>
                                <div class="row row-in-registration">
                                    <div class="col-xl-6">
                                        <div class="form-group">
                                            <label for="type"><fmt:message key="type"/></label>
                                            <select class="form-select" id="type" name="typeId" >
                                                <c:forEach var="type" items="${goodTypes}">
                                                    <option ${type.name == good.type.name ? 'selected' : ''} value="${type.id}"><c:out value="${type.name}"/></option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xl-6">
                                        <div class="form-group">
                                            <label for="good-picture"><fmt:message key="good.picture"/> </label>
                                            <div class="mb-3">
                                                <input class="form-control" type="file" name="good-picture" id="good-picture">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <input type="hidden" name="goodId" value="${good.id}">
                                <button type="submit" id="good-button"
                                        class="btn btn-secondary authorization authorization-button">
                                    <fmt:message key="change"/>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <script src="<c:url value="/js/goodValidator.js"/>"></script>
<c:import url="footer.jsp"/>