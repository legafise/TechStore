<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>

<c:url value="/controller?command=update_profile" var="updateProfileCommand"/>

<!DOCTYPE HTML>
<html>
<head>
    <title>Изменение профиля</title>
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
                            Изменение профиля
                        </div>
                        <div class="card-body authorization">
                            <form action="${updateProfileCommand}" method="post" class="reg-inputs"
                                  enctype="multipart/form-data">
                                <div class="row row-in-registration">
                                    <div class="col-xl-6">
                                        <div class="form-group">
                                            <label for="name"><fmt:message key="name"/></label>
                                            <input type="text" value="${user.name}" class="form-control" id="name" name="name">
                                        </div>
                                    </div>
                                    <div class="col-xl-6">
                                        <div class="form-group">
                                            <label for="surname"><fmt:message key="surname"/></label>
                                            <input type="text" class="form-control" value="${user.surname}" id="surname" name="surname">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="profile-photo"><fmt:message key="profile.photo"/></label>
                                    <div class="mb-3">
                                        <input class="form-control" type="file" name="profilePhoto" id="profile-photo">
                                    </div>
                                </div>
                                <div class="row row-in-registration">
                                    <div class="col-xl-6">
                                        <div class="form-group">
                                            <label for="birth-date"><fmt:message key="birth.date"/></label>
                                            <input type="date" value="${user.birthDate}" class="form-control" id="birth-date" name="birthDate">
                                        </div>
                                    </div>
                                    <div class="col-xl-6">
                                        <label for="email">Email</label>
                                        <input type="email" value="${user.email}" class="form-control" id="email" name="email"
                                               aria-describedby="emailHelp">
                                    </div>
                                </div>
                                <br/>
                                <button type="submit" class="btn btn-secondary authorization authorization-button ">
                                    Изменить
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <script src="<c:url value="/js/userUpdatingValidator.js"/>"></script>
    <c:import url="footer.jsp"/>