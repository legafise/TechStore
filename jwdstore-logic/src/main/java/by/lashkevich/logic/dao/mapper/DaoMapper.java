package by.lashkevich.logic.dao.mapper;

import by.lashkevich.logic.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoMapper {
    private static final String GOOD_ID = "good_id";
    private static final String ORDER_ID = "order_id";
    private static final String ORDER_STATUS = "order_status";
    private static final String REVIEW_ID = "review_id";
    private static final String REVIEW_CONTENT = "review_content";
    private static final String REVIEW_RATE = "review_rate";
    private static final String GOOD_NAME = "good_name";
    private static final String USER_NAME = "user_name";
    private static final String USER_SURNAME = "user_surname";
    private static final String USER_LOGIN = "user_login";
    private static final String USER_PASSWORD = "user_password";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_BIRTH_DATE = "user_birth_date";
    private static final String USER_PROFILE_PICTURE = "user_profile_picture";
    private static final String USER_BALANCE = "user_balance";
    private static final String USER_ROLE = "user_role";
    private static final String USER_ID = "user_id";
    private static final String REVIEW_USER_NAME = "review_user_name";
    private static final String REVIEW_USER_ID = "review_user_id";
    private static final String REVIEW_USER_SURNAME = "review_user_surname";
    private static final String REVIEW_USER_LOGIN = "review_user_login";
    private static final String REVIEW_USER_PASSWORD = "review_user_password";
    private static final String REVIEW_USER_EMAIL = "review_user_email";
    private static final String REVIEW_USER_BIRTH_DATE = "review_user_birth_date";
    private static final String REVIEW_USER_PROFILE_PICTURE = "review_user_profile_picture";
    private static final String REVIEW_USER_BALANCE = "review_user_balance";
    private static final String REVIEW_USER_ROLE = "review_user_role";
    private static final String GOOD_PRICE = "good_price";
    private static final String ORDER_PRICE = "order_price";
    private static final String ORDER_DATE = "order_date";
    private static final String ORDER_ADDRESS = "order_address";
    private static final String GOOD_DESCRIPTION = "good_description";
    private static final String GOOD_TYPE = "good_type_name";
    private static final String GOOD_PICTURE = "good_picture";
    private static final String GOOD_QUANTITY = "good_quantity";

    public User mapUser(ResultSet resultSet) throws SQLException {
        return fillUserData(resultSet, USER_ID, USER_NAME, USER_SURNAME, USER_LOGIN, USER_PASSWORD,
                USER_EMAIL, USER_BIRTH_DATE, USER_PROFILE_PICTURE, USER_BALANCE, USER_ROLE);
    }

    public Good mapGood(ResultSet resultSet) throws SQLException {
        Good good = new Good();

        while (resultSet.next()) {
            if (resultSet.getInt(GOOD_ID) != good.getId() && good.getId() != 0) {
                resultSet.previous();
                return good;
            }

            if (good.getId() == 0) {
                fillGoodData(good, resultSet);
            }

            if (resultSet.getInt(GOOD_ID) == good.getId()) {
                good.getReviews().add(mapReview(resultSet));
            }
        }

        return good;
    }

    public Review mapReview(ResultSet resultSet) throws SQLException {
        Review review = new Review();
        review.setId(resultSet.getLong(REVIEW_ID));
        review.setRate(resultSet.getFloat(REVIEW_RATE));
        review.setContent(resultSet.getString(REVIEW_CONTENT));
        review.setAuthor(mapReviewUser(resultSet));
        return review;
    }

    private User mapReviewUser(ResultSet resultSet) throws SQLException {
        return fillUserData(resultSet, REVIEW_USER_ID, REVIEW_USER_NAME, REVIEW_USER_SURNAME, REVIEW_USER_LOGIN,
                REVIEW_USER_PASSWORD, REVIEW_USER_EMAIL, REVIEW_USER_BIRTH_DATE, REVIEW_USER_PROFILE_PICTURE,
                REVIEW_USER_BALANCE, REVIEW_USER_ROLE);
    }

    public Order mapOrder(ResultSet resultSet) throws SQLException, OrderStatusException {
        Order order = new Order();

        while (resultSet.next()) {
            if (resultSet.getInt(ORDER_ID) != order.getId() && order.getId() != 0) {
                resultSet.previous();
                return order;
            }

            if (order.getId() == 0) {
                fillOrderData(order, resultSet);
            }

            if (resultSet.getInt(ORDER_ID) == order.getId()) {
                int quantity = resultSet.getInt(GOOD_QUANTITY);
                order.getGoods().put(mapOrderedGood(resultSet, resultSet.getInt(ORDER_ID)), quantity);
            }
        }

        return order;
    }

    private Good mapOrderedGood(ResultSet resultSet, int orderId) throws SQLException {
        Good good = new Good();

        while (!resultSet.isAfterLast()) {
            if (resultSet.getInt(ORDER_ID) != orderId) {
                resultSet.previous();
                return good;
            }

            if (resultSet.getInt(GOOD_ID) != good.getId() && good.getId() != 0) {
                resultSet.previous();
                return good;
            }

            if (good.getId() == 0) {
                fillGoodData(good, resultSet);
            }

            if (resultSet.getInt(GOOD_ID) == good.getId()) {
                good.getReviews().add(mapReview(resultSet));
                resultSet.next();
            }
        }

        return good;
    }

    private void fillGoodData(Good good, ResultSet resultSet) throws SQLException {
        good.setId(resultSet.getLong(GOOD_ID));
        good.setName(resultSet.getString(GOOD_NAME));
        good.setPrice(resultSet.getBigDecimal(GOOD_PRICE));
        good.setDescription(resultSet.getString(GOOD_DESCRIPTION));
        good.setType(resultSet.getString(GOOD_TYPE));
        good.setImgURL(resultSet.getString(GOOD_PICTURE));
    }

    private void fillOrderData(Order order, ResultSet resultSet) throws SQLException, OrderStatusException {
        order.setId(resultSet.getLong(ORDER_ID));
        order.setStatus(OrderStatus.findStatus(resultSet.getString(ORDER_STATUS)));
        order.setPrice(resultSet.getBigDecimal(ORDER_PRICE));
        order.setAddress(resultSet.getString(ORDER_ADDRESS));
        order.setDate(resultSet.getDate(ORDER_DATE).toLocalDate());
        order.setCustomer(mapUser(resultSet));
    }

    private User fillUserData(ResultSet resultSet, String idColumn, String nameColumn, String surnameColumn,
                              String loginColumn, String passwordColumn, String emailColumn, String birthDateColumn,
                              String pictureColumn, String balanceColumn, String roleColumn) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(idColumn));
        user.setName(resultSet.getString(nameColumn));
        user.setSurname(resultSet.getString(surnameColumn));
        user.setLogin(resultSet.getString(loginColumn));
        user.setPassword(resultSet.getString(passwordColumn));
        user.setEmail(resultSet.getString(emailColumn));
        user.setBirthDate(resultSet.getDate(birthDateColumn).toLocalDate());
        user.setProfilePicture(resultSet.getString(pictureColumn));
        user.setBalance(resultSet.getBigDecimal(balanceColumn));
        user.setRole(Role.findRole(resultSet.getInt(roleColumn)));
        return user;
    }
}
