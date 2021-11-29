<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/download/" var="imgPath"/>
<c:url value="/css/card.css" var="cardCssPath"/>
<c:url value="/controller?command=payment" var="paymentCommand"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="languages.keywords"/>

<!DOCTYPE HTML>
<html>
<head>
    <title><fmt:message key="balance.replenishment"/></title>
    <link rel="stylesheet" href="${cardCssPath}">
    <c:import url="header.jsp"/>
    <main>
        <c:if test="${isInvalidCardData == true}">
            <div class="container-fluid authorization-result">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong><fmt:message key="invalid.card.data"/></strong> <fmt:message key="try.again"/>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <%request.getSession().removeAttribute("isInvalidCardData");%>
        </c:if>
        <div class="container-fluid name">
            <p align="center" class="h3 bold"><fmt:message key="top.up.amount"/> ${amount} <fmt:message key="currency.sign"/></p>
        </div>
        <form action="${paymentCommand}" method="post">
            <div class="wrapper" id="app">
                <div class="card-form">
                    <div class="card-list">
                        <div class="card-item" v-bind:class="{ '-active' : isCardFlipped }">
                            <div class="card-item__side -front">
                                <div class="card-item__focus" v-bind:class="{'-active' : focusElementStyle }"
                                     v-bind:style="focusElementStyle" ref="focusElement"></div>
                                <div class="card-item__cover">
                                    <img
                                            v-bind:src="'https://raw.githubusercontent.com/muhammederdem/credit-card-form/master/src/assets/images/' + currentCardBackground + '.jpeg'"
                                            class="card-item__bg">
                                </div>

                                <div class="card-item__wrapper">
                                    <div class="card-item__top">
                                        <img
                                                src="https://raw.githubusercontent.com/muhammederdem/credit-card-form/master/src/assets/images/chip.png"
                                                class="card-item__chip">
                                        <div class="card-item__type">
                                            <transition name="slide-fade-up">
                                                <img
                                                        v-bind:src="'https://raw.githubusercontent.com/muhammederdem/credit-card-form/master/src/assets/images/' + getCardType + '.png'"
                                                        v-if="getCardType" v-bind:key="getCardType" alt=""
                                                        class="card-item__typeImg">
                                            </transition>
                                        </div>
                                    </div>
                                    <label for="cardNumber" class="card-item__number" ref="cardNumber">
                                        <template v-if="getCardType === 'amex'">
                  <span v-for="(n, $index) in amexCardMask" :key="$index">
                    <transition name="slide-fade-up">
                      <div class="card-item__numberItem"
                           v-if="$index > 4 && $index < 14 && cardNumber.length > $index && n.trim() !== ''">*</div>
                      <div class="card-item__numberItem" :class="{ '-active' : n.trim() === '' }" :key="$index"
                           v-else-if="cardNumber.length > $index">
                        {{cardNumber[$index]}}
                      </div>
                      <div class="card-item__numberItem" :class="{ '-active' : n.trim() === '' }" v-else
                           :key="$index + 1">{{n}}</div>
                    </transition>
                  </span>
                                        </template>

                                        <template v-else>
                  <span v-for="(n, $index) in otherCardMask" :key="$index">
                    <transition name="slide-fade-up">
                      <div class="card-item__numberItem"
                           v-if="$index > 4 && $index < 15 && cardNumber.length > $index && n.trim() !== ''">*</div>
                      <div class="card-item__numberItem" :class="{ '-active' : n.trim() === '' }" :key="$index"
                           v-else-if="cardNumber.length > $index">
                        {{cardNumber[$index]}}
                      </div>
                      <div class="card-item__numberItem" :class="{ '-active' : n.trim() === '' }" v-else
                           :key="$index + 1">{{n}}</div>
                    </transition>
                  </span>
                                        </template>
                                    </label>
                                    <div class="card-item__content">
                                        <label for="cardName" class="card-item__info" ref="cardName">
                                            <div class="card-item__holder"><fmt:message key="card.holder"/></div>
                                            <transition name="slide-fade-up">
                                                <div class="card-item__name" v-if="cardName.length" key="1">
                                                    <transition-group name="slide-fade-right">
                        <span class="card-item__nameItem" v-for="(n, $index) in cardName.replace(/\s\s+/g, ' ')"
                              v-if="$index === $index" v-bind:key="$index + 1">{{n}}</span>
                                                    </transition-group>
                                                </div>
                                                <div class="card-item__name" v-else key="2"><fmt:message key="full.name"/></div>
                                            </transition>
                                        </label>
                                        <div class="card-item__date" ref="cardDate">
                                            <label for="cardMonth" class="card-item__dateTitle"><fmt:message key="expires"/></label>
                                            <label for="cardMonth" class="card-item__dateItem">
                                                <transition name="slide-fade-up">
                                                    <span v-if="cardMonth" v-bind:key="cardMonth">{{cardMonth}}</span>
                                                    <span v-else key="2">MM</span>
                                                </transition>
                                            </label>
                                            /
                                            <label for="cardYear" class="card-item__dateItem">
                                                <transition name="slide-fade-up">
                                                    <span v-if="cardYear" v-bind:key="cardYear">{{String(cardYear).slice(2,4)}}</span>
                                                    <span v-else key="2">YY</span>
                                                </transition>
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card-item__side -back">
                                <div class="card-item__cover">
                                    <img
                                            v-bind:src="'https://raw.githubusercontent.com/muhammederdem/credit-card-form/master/src/assets/images/' + currentCardBackground + '.jpeg'"
                                            class="card-item__bg">
                                </div>
                                <div class="card-item__band"></div>
                                <div class="card-item__cvv">
                                    <div class="card-item__cvvTitle">CVV</div>
                                    <div class="card-item__cvvBand">
                <span v-for="(n, $index) in cardCvv" :key="$index">
                  *
                </span>

                                    </div>
                                    <div class="card-item__type">
                                        <img
                                                v-bind:src="'https://raw.githubusercontent.com/muhammederdem/credit-card-form/master/src/assets/images/' + getCardType + '.png'"
                                                v-if="getCardType" class="card-item__typeImg">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-form__inner">
                        <div class="card-input">
                            <label for="cardNumber" class="card-input__label"><fmt:message key="card.number"/></label>
                            <input type="text" id="cardNumber" class="card-input__input" v-mask="generateCardNumberMask"
                                   v-model="cardNumber" v-on:focus="focusInput" v-on:blur="blurInput"
                                   data-ref="cardNumber"
                                   name="number"
                                   autocomplete="off">
                        </div>
                        <div class="card-input">
                            <label for="cardName" class="card-input__label"><fmt:message key="card.holder"/></label>
                            <input type="text" id="cardName" class="card-input__input" v-model="cardName"
                                   v-on:focus="focusInput" name="name"
                                   v-on:blur="blurInput" data-ref="cardName" autocomplete="off">
                        </div>
                        <div class="card-form__row">
                            <div class="card-form__col">
                                <div class="card-form__group">
                                    <label for="cardMonth" class="card-input__label"><fmt:message key="expiration.date"/> </label>
                                    <select class="card-input__input -select" id="cardMonth" v-model="cardMonth"
                                            v-on:focus="focusInput"
                                            name="month"
                                            v-on:blur="blurInput" data-ref="cardDate">
                                        <option value="" disabled selected><fmt:message key="month"/> </option>
                                        <option v-bind:value="n < 10 ? '0' + n : n" v-for="n in 12"
                                                v-bind:disabled="n < minCardMonth"
                                                v-bind:key="n">
                                            {{n < 10 ? '0' + n : n}}
                                        </option>
                                    </select>
                                    <select class="card-input__input -select" id="cardYear" v-model="cardYear"
                                            v-on:focus="focusInput" name="year"
                                            v-on:blur="blurInput" data-ref="cardDate">
                                        <option value="" disabled selected><fmt:message key="year"/></option>
                                        <option v-bind:value="$index + minCardYear" v-for="(n, $index) in 12"
                                                v-bind:key="n">
                                            {{$index + minCardYear}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="card-form__col -cvv">
                                <div class="card-input">
                                    <label for="cardCvv" class="card-input__label">CVV</label>
                                    <input type="text" class="card-input__input" id="cardCvv" v-mask="'####'"
                                           maxlength="4"
                                           name="cvv"
                                           v-model="cardCvv"
                                           v-on:focus="flipCard(true)" v-on:blur="flipCard(false)" autocomplete="off">
                                </div>
                            </div>
                        </div>

                        <input type="hidden" name="amount" value="${amount}"/>

                        <button type="submit" class="card-form__button">
                            <fmt:message key="send"/>
                        </button>
                    </div>
                </div>
            </div>
        </form>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.6.10/vue.min.js"></script>
        <script src="https://unpkg.com/vue-the-mask@0.11.1/dist/vue-the-mask.js"></script>
        <script language="JavaScript" type="text/javascript" src="<c:url value="/js/card.js"/>"></script>
    </main>
    <c:import url="footer.jsp"/>