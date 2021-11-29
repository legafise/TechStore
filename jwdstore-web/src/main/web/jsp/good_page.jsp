<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/download/" var="imgPath"/>
<c:url value="/controller?command=handle_review" var="sendReviewCommand"/>
<c:url value="/controller?command=remove_review" var="removeReviewCommand"/>
<c:url value="/controller?command=add_good_in_basket" var="addGoodInBasket"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>

<!DOCTYPE HTML>
<html>
<head>
    <title>${good.name}</title>
    <c:import url="header.jsp"/>
    <main>
        <c:if test="${basketAddingResult == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong>Товар добавлен в корзину!</strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("basketAddingResult");%>
        </c:if>
        <c:if test="${reviewAddResult == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="positive.review.add.result"/></strong> <fmt:message
                        key="positive.review.add.result.info"/>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("reviewAddResult");%>
        </c:if>
        <c:if test="${reviewAddResult == false || isReviewRemoved == false || basketAddingResult == false}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="something.went.wrong"/></strong> <fmt:message key="try.again"/>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("reviewAddResult");%>
            <%request.getSession().removeAttribute("isReviewRemoved");%>
            <%request.getSession().removeAttribute("basketAddingResult");%>
        </c:if>
        <c:if test="${isReviewRemoved == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="positive.review.remove.result"/></strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("isReviewRemoved");%>
        </c:if>
        <div class="container-fluid name">
            <p class="h2 bold">${good.name}</p>
        </div>
        <div class="container-fluid good-idents">
            <div class="row">
                <div class="col-xl-4">
                    <img src="${imgPath}goods_${good.imgURL}" class="img-fluid img-indents" alt="iphone">
                    <p class="price"><fmt:message key="price"/>: ${good.price} <fmt:message key="currency.sign"/></p>
                    <div class="buttons-idents">
                        <button class="buy-button">
                            <fmt:message key="buy"/>
                        </button>
                        <form method="post" action="${addGoodInBasket}">
                            <input type="hidden" value="${good.id}" name="goodId">
                            <button class="basket-button" type="submit">
                                <fmt:message key="add.to.cart"/>
                            </button>
                        </form>
                    </div>
                </div>
                <div class="col-xl-6 description">
                    <span class="bold"><fmt:message key="type"/> </span>${good.type} <br>
                    <span class="bold"><fmt:message key="description"/> </span>${good.description}
                </div>
            </div>
        </div>
        <br>
        <div class="container-fluid">
            <p class="h2 bold" align="center"><fmt:message key="reviews"/></p>
        </div>
        <c:if test="${role != 'guest'}">
            <c:choose>
                <c:when test="${isCratedReview == false}">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-lg-1"></div>
                            <div class="col-lg-10">
                                <form action="${sendReviewCommand}" method="post" class="review-indents review-inputs"
                                      id="review-inputs">
                                    <div class="card">
                                        <div class="input-group">
                                            <textarea name="reviewContent" id="review-content"
                                                      class="form-control review-area" aria-label="With textarea"
                                                      placeholder="Оставьте свой отзыв!" maxlength="400"></textarea>
                                        </div>
                                        <input type="hidden" value="${good.id}" name="goodId">
                                        <div class="card-footer">
                                            <div class="row">
                                                <div class="col-lg-11">
                                                    <label for="customRange3" class="bold"><fmt:message
                                                            key="rate.the.good"/></label>
                                                    <input type="range" class="custom-range" min="1" max="5"
                                                           step="1"
                                                           id="customRange3" name="rate">
                                                </div>
                                                <div class="col-lg-1">
                                                    <button type="submit"
                                                            class="btn btn-outline-secondary review-button">
                                                        <fmt:message key="send"/>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:when test="${isCratedReview == true}">
                    <div class="container-fluid">
                        <p class="h2 bold" align="center"><fmt:message key="review.already.exists"/></p>
                    </div>
                </c:when>
            </c:choose>
        </c:if>
        <c:if test="${good.reviews.size() == 0}">
            <div class="container-fluid">
                <p class="h2 bold" align="center"><fmt:message key="no.reviews"/></p>
            </div>
        </c:if>
        <c:if test="${good.reviews.size() != 0}">
            <c:forEach var="review" items="${good.reviews}">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-1"></div>
                        <div class="col-lg-10">
                            <div class="card review">
                                <div class="card-body">
                                    <div class="container-fluid">
                                        <div class="row">
                                            <p class="h4">${review.author.login}</p>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-2">
                                                <img class="review-img"
                                                     src="${imgPath}users_${review.author.profilePictureName}"
                                                     alt="profile picture"/>
                                            </div>
                                            <div class="col-lg-8">
                                                <div class="review-content">${review.content}</div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-11">
                                                <div class="rating-mini">
                                                    <c:choose>
                                                        <c:when test="${review.rate == 1}">
                                                            <span class="active"></span>
                                                            <span></span>
                                                            <span></span>
                                                            <span></span>
                                                            <span></span>
                                                        </c:when>
                                                        <c:when test="${review.rate == 2}">
                                                            <span class="active"></span>
                                                            <span class="active"></span>
                                                            <span></span>
                                                            <span></span>
                                                            <span></span>
                                                        </c:when>
                                                        <c:when test="${review.rate == 3}">
                                                            <span class="active"></span>
                                                            <span class="active"></span>
                                                            <span class="active"></span>
                                                            <span></span>
                                                            <span></span>
                                                        </c:when>
                                                        <c:when test="${review.rate == 4}">
                                                            <span class="active"></span>
                                                            <span class="active"></span>
                                                            <span class="active"></span>
                                                            <span class="active"></span>
                                                            <span></span>
                                                        </c:when>
                                                        <c:when test="${review.rate == 5}">
                                                            <span class="active"></span>
                                                            <span class="active"></span>
                                                            <span class="active"></span>
                                                            <span class="active"></span>
                                                            <span class="active"></span>
                                                        </c:when>
                                                    </c:choose>
                                                </div>
                                            </div>
                                            <div class="col-lg-1">
                                                <c:if test="${review.author.id == userId}">
                                                    <form method="post" action="${removeReviewCommand}">
                                                        <input type="hidden" value="${review.id}" name="reviewId">
                                                        <button type="submit" class="btn delete-button"><fmt:message
                                                                key="remove"/></button>
                                                    </form>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </main>

    <script src="<c:url value="/js/reviewValidator.js"/>"></script>
    <c:import url="footer.jsp"/>
