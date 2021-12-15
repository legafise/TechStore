<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>

<c:url value="/controller?command=add_good" var="addGoodCommand"/>

<!DOCTYPE HTML>
<html>
<head>
    <title><fmt:message key="good.creation"/> </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <c:import url="header.jsp"/>
    <main>
        <c:if test="${isGoodAdded == false}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="something.went.wrong"/> </strong><fmt:message key="try.again"/>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("isGoodAdded");%>
        </c:if>
        <div class="container-fluid">
            <div class="row">
                <div class="col-xl-4"></div>
                <div class="col-xl-4">
                    <div class="card text-center registration-form-idents">
                        <div class="card-header authorization bold">
                            <fmt:message key="good.creation"/>
                        </div>
                        <div class="card-body authorization">
                            <form action="${addGoodCommand}" method="post" id="good-inputs" class="reg-inputs"
                                  enctype="multipart/form-data">
                                <div class="row row-in-registration">
                                    <div class="col-xl-6">
                                        <div class="form-group">
                                            <label for="name"><fmt:message key="name"/></label>
                                            <input type="text" class="form-control" id="name" name="name">
                                        </div>
                                    </div>
                                    <div class="col-xl-6">
                                        <div class="form-group">
                                            <label for="price"><fmt:message key="price"/></label>
                                            <input type="number" step="0.01" min="1" max="1000000" placeholder="0,00"
                                                   class="form-control" id="price" name="price">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="description"><fmt:message key="description"/></label>
                                    <textarea name="description" id="description"
                                              class="form-control review-area" aria-label="With textarea"
                                              placeholder="<fmt:message key="enter.information.about.good"/>" maxlength="400"></textarea>
                                </div>
                                <div class="row row-in-registration">
                                    <div class="col-xl-6">
                                        <div class="form-group">
                                            <label for="type"><fmt:message key="type"/></label>
                                            <select class="form-select" id="type" name="typeId" >
                                                <option selected><fmt:message key="chose.type"/></option>
                                                <c:forEach var="type" items="${goodTypes}">
                                                    <option value="${type.id}"><c:out value="${type.name}"/></option>
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
                                <button disabled type="submit"
                                        class="btn btn-secondary authorization authorization-button" id="good-button">
                                    <fmt:message key="add"/>
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