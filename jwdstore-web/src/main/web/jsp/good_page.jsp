<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/download/" var="imgPath"/>
<c:url value="/controller?command=handle_review" var="sendReviewCommand"/>
<c:url value="/controller?command=remove_review" var="removeReviewCommand"/>
<c:url value="/controller?command=add_good_in_basket" var="addGoodInBasket"/>
<c:url value="/controller?command=place_order_by_buy_button" var="placeOrderByBuyButton"/>
<c:url value="/controller?command=remove_good" var="removeGoodCommand"/>
<c:url value="/controller?command=update_good_page" var="updateGoodPageCommand"/>
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
                    <strong><fmt:message key="good.added.to.basket"/> </strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("basketAddingResult");%>
        </c:if>
        <c:if test="${isGoodUpdated == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="good.has.been.changed"/> </strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("isGoodUpdated");%>
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
        <c:if test="${reviewAddResult == false || isGoodRemoved == false || isReviewRemoved == false
         || basketAddingResult == false || isGoodUpdated == false}">
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
            <%request.getSession().removeAttribute("isGoodRemoved");%>
            <%request.getSession().removeAttribute("isGoodUpdated");%>
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
            <p class="h2 bold"><c:out value="${good.name}"/></p>
        </div>
        <div class="container-fluid good-idents">
            <div class="row">
                <div class="col-xl-4">
                    <img src="${imgPath}goods_${good.imgName}" class="img-fluid img-indents" alt="${good.name}">
                    <p class="price"><fmt:message key="price"/>: ${good.price} <fmt:message key="currency.sign"/></p>
                    <div class="buttons-idents">
                        <c:choose>
                            <c:when test="${role != 'moder' && role != 'admin'}">
                                <form action="${placeOrderByBuyButton}" method="post">
                                    <input type="hidden" name="goodId" value="${good.id}">
                                    <input type="hidden" name="quantity" value="1">
                                    <button type="submit" class="buy-button">
                                        <fmt:message key="buy"/>
                                    </button>
                                </form>
                                <form method="post" action="${addGoodInBasket}">
                                    <input type="hidden" value="${good.id}" name="goodId">
                                    <button class="basket-button" type="submit">
                                        <fmt:message key="add.to.basket"/>
                                    </button>
                                </form>
                            </c:when>
                            <c:when test="${role == 'moder' || role == 'admin'}">
                                <form action="${updateGoodPageCommand}" method="post">
                                    <input type="hidden" name="goodId" value="${good.id}">
                                    <button type="submit" class="buy-button">
                                        <fmt:message key="change"/>
                                    </button>
                                </form>
                                <form method="post" action="${removeGoodCommand}">
                                    <input type="hidden" value="${good.id}" name="goodId">
                                    <button class="delete-button good-delete-button" type="submit">
                                        <fmt:message key="remove"/>
                                    </button>
                                </form>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
                <div class="col-xl-6 description">
                    <span class="bold"><fmt:message key="type"/>: </span><c:out value="${good.type.name}"/> <br>
                    <span class="bold"><fmt:message key="description"/>: </span><c:out value="${good.description}"/>
                </div>
            </div>
        </div>
        <br>
        <div class="container-fluid">
            <p class="h2 bold" align="center"><fmt:message key="reviews"/></p>
        </div>
        <c:if test="${role == 'user'}">
            <c:if test="${isBoughtGood == false}">
                <div class="container-fluid">
                    <p class="h4" align="center"><fmt:message key="buy.good.to.leave.review"/> </p>
                </div>
            </c:if>
            <c:if test="${isBoughtGood == true}">
                <c:choose>
                    <c:when test="${isCratedReview == false}">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-lg-1"></div>
                                <div class="col-lg-10">
                                    <div class="container-fluid">
                                        <p class="h4" align="center"><fmt:message key="leave.review"/> </p>
                                    </div>
                                    <form action="${sendReviewCommand}" method="post" class="review-indents review-inputs"
                                          id="review-inputs">
                                        <div class="card">
                                            <div class="input-group">
                                                <textarea name="reviewContent" id="review-content"
                                                          class="form-control review-area" aria-label="With textarea"
                                                          placeholder="<fmt:message key="leave.your.review"/>" maxlength="400"></textarea>
                                            </div>
                                            <input type="hidden" value="${good.id}" name="goodId">
                                            <div class="card-footer">
                                                <div class="row">
                                                    <div class="col-lg-10">
                                                        <label for="customRange3" class="bold"><fmt:message
                                                            key="rate.the.good"/></label>
                                                        <input type="range" class="custom-range" min="1" max="5"
                                                               step="1"
                                                        id="customRange3" name="rate" onchange="showRange()">
                                                    </div>
                                                    <div class="col-lg-1">
                                                        <span id="rate"><span id="rate-value">3</span><span id="review-star">&#9733;</span></span>
                                                    </div>
                                                    <div class="col-lg-1">
                                                        <button type="submit"
                                                                class="btn btn-outline-secondary review-button" disabled>
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
        </c:if>
        <c:if test="${good.reviews.size() == 0}">
            <div class="container-fluid">
                <p class="h4" align="center"><fmt:message key="no.reviews"/></p>
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
                                            <p class="h4"><c:out value="${review.author.login}"/></p>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-2">
                                                <img class="review-img"
                                                     src="${imgPath}users_${review.author.profilePictureName}"
                                                     alt="profile picture"/>
                                            </div>
                                            <div class="col-lg-8">
                                                <div class="review-content"><c:out value="${review.content}"/></div>
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
                                                <c:if test="${review.author.id == userId || role == 'moder' || role == 'admin'}">
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
