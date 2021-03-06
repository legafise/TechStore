package by.lashkevich.logic.dao.mapper;

import by.lashkevich.logic.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Class for mapping objects in dao.
 * @author Roman Lashkevich
 */
public class DaoMapper {
    private static final String GOOD_ID = "good_id";
    private static final String ORDER_ID = "order_id";
    private static final String ORDER_STATUS = "status";
    private static final String REVIEW_ID = "review_id";
    private static final String REVIEW_CONTENT = "content";
    private static final String REVIEW_RATE = "rate";
    private static final String GOOD_NAME = "good_name";
    private static final String USER_NAME = "user_name";
    private static final String USER_SURNAME = "surname";
    private static final String USER_LOGIN = "login";
    private static final String USER_PASSWORD = "password";
    private static final String USER_EMAIL = "email";
    private static final String USER_BIRTH_DATE = "birth_date";
    private static final String USER_PROFILE_PICTURE = "profile_picture";
    private static final String USER_BALANCE = "balance";
    private static final String USER_ROLE = "role";
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
    private static final String ORDER_DATE = "date";
    private static final String ORDER_ADDRESS = "address";
    private static final String GOOD_DESCRIPTION = "description";
    private static final String GOOD_TYPE = "good_type_name";
    private static final String GOOD_TYPE_ID = "good_type_id";
    private static final String GOOD_PICTURE = "picture";
    private static final String GOOD_QUANTITY = "quantity";

    /**
     * Map user user.
     *
     * @param resultSet the result set
     * @return the user
     * @throws SQLException the sql exception
     */
    public User mapUser(ResultSet resultSet) throws SQLException {
        return fillUserData(resultSet, USER_ID, USER_NAME, USER_SURNAME, USER_LOGIN, USER_PASSWORD,
                USER_EMAIL, USER_BIRTH_DATE, USER_PROFILE_PICTURE, USER_BALANCE, USER_ROLE);
    }

    /**
     * Map review review.
     *
     * @param resultSet the result set
     * @return the review
     * @throws SQLException the sql exception
     */
    public Review mapReview(ResultSet resultSet) throws SQLException {
        Review review = new Review();
        review.setId(resultSet.getLong(REVIEW_ID));
        review.setRate(resultSet.getShort(REVIEW_RATE));
        review.setContent(resultSet.getString(REVIEW_CONTENT));
        review.setAuthor(mapReviewUser(resultSet));
        return review;
    }

    private User mapReviewUser(ResultSet resultSet) throws SQLException {
        return fillUserData(resultSet, REVIEW_USER_ID, REVIEW_USER_NAME, REVIEW_USER_SURNAME, REVIEW_USER_LOGIN,
                REVIEW_USER_PASSWORD, REVIEW_USER_EMAIL, REVIEW_USER_BIRTH_DATE, REVIEW_USER_PROFILE_PICTURE,
                REVIEW_USER_BALANCE, REVIEW_USER_ROLE);
    }

    /**
     * Map good good.
     *
     * @param resultSet the result set
     * @return the good
     * @throws SQLException the sql exception
     */
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

            if (resultSet.getInt(GOOD_ID) == good.getId() && resultSet.getLong(REVIEW_ID) != 0) {
                good.getReviews().add(mapReview(resultSet));
            }
        }

        return good;
    }

    private Good mapOrderedGood(ResultSet resultSet, long orderId) throws SQLException {
        Good good = new Good();

        while (!resultSet.isAfterLast()) {
            if (resultSet.getInt(ORDER_ID) != orderId) {
                resultSet.previous();
                return good;
            }

            Optional<Good> goodOptional = fillGoodData(resultSet, good);
            if (goodOptional.isPresent()) {
                return good;
            }
        }

        return good;
    }

    /**
     * Map order order.
     *
     * @param resultSet the result set
     * @return the order
     * @throws SQLException         the sql exception
     * @throws OrderStatusException the order status exception
     */
    public Order mapOrder(ResultSet resultSet) throws SQLException, OrderStatusException {
        Order order = new Order();

        while (resultSet.next()) {
            order.setCustomer(mapUser(resultSet));

            if (resultSet.getInt(ORDER_ID) == 0 && resultSet.getString(ORDER_STATUS) == null) {
                resultSet.next();
                return order;
            }

            if (order.getId() == 0) {
                fillOrderData(order, resultSet);
            }

            if (resultSet.getInt(ORDER_ID) != order.getId() && order.getId() != 0) {
                resultSet.previous();
                return order;
            }

            if (resultSet.getInt(GOOD_ID) == 0 && resultSet.getLong(ORDER_ID) != 0) {
                resultSet.next();
                return order;
            }

            if (resultSet.getInt(ORDER_ID) == order.getId()) {
                short quantity = resultSet.getShort(GOOD_QUANTITY);
                order.getGoods().put(mapOrderedGood(resultSet, resultSet.getLong(ORDER_ID)), quantity);
            }
        }

        return order;
    }

    private Good mapGoodInBasket(ResultSet resultSet) throws SQLException {
        Good good = new Good();

        while (!resultSet.isAfterLast()) {
            Optional<Good> goodOptional = fillGoodData(resultSet, good);
            if (goodOptional.isPresent()) {
                return good;
            }
        }

        resultSet.previous();
        return good;
    }

    /**
     * Map basket basket.
     *
     * @param resultSet the result set
     * @return the basket
     * @throws SQLException the sql exception
     */
    public Basket mapBasket(ResultSet resultSet) throws SQLException {
        Basket basket = new Basket();
        resultSet.next();
        basket.setOwner(mapUser(resultSet));

        while (!resultSet.isAfterLast()) {
            basket.getGoods().put(mapGoodInBasket(resultSet), resultSet.getShort(GOOD_QUANTITY));
            resultSet.next();
        }

        return basket;
    }

    private void fillGoodData(Good good, ResultSet resultSet) throws SQLException {
        good.setId(resultSet.getLong(GOOD_ID));
        good.setName(resultSet.getString(GOOD_NAME));
        good.setPrice(resultSet.getBigDecimal(GOOD_PRICE));
        good.setDescription(resultSet.getString(GOOD_DESCRIPTION));
        good.setType(mapGoodType(resultSet));
        good.setImgName(resultSet.getString(GOOD_PICTURE));
    }

    /**
     * Map good type good type.
     *
     * @param resultSet the result set
     * @return the good type
     * @throws SQLException the sql exception
     */
    public GoodType mapGoodType(ResultSet resultSet) throws SQLException {
        GoodType goodType = new GoodType();
        goodType.setId(resultSet.getShort(GOOD_TYPE_ID));
        goodType.setName(resultSet.getString(GOOD_TYPE));
        return goodType;
    }

    private void fillOrderData(Order order, ResultSet resultSet) throws SQLException, OrderStatusException {
        order.setId(resultSet.getLong(ORDER_ID));
        order.setStatus(OrderStatus.findStatus(resultSet.getString(ORDER_STATUS)));
        order.setPrice(resultSet.getBigDecimal(ORDER_PRICE));
        order.setAddress(resultSet.getString(ORDER_ADDRESS));
        order.setDate(resultSet.getDate(ORDER_DATE).toLocalDate());
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
        user.setProfilePictureName(resultSet.getString(pictureColumn));
        user.setBalance(resultSet.getBigDecimal(balanceColumn));
        user.setRole(Role.findRoleByNumber(resultSet.getInt(roleColumn)));
        return user;
    }

    private Optional<Good> fillGoodData(ResultSet resultSet, Good good) throws SQLException {
        if (resultSet.getInt(GOOD_ID) != good.getId() && good.getId() != 0) {
            resultSet.previous();
            return Optional.of(good);
        }

        if (good.getId() == 0) {
            fillGoodData(good, resultSet);
        }

        if (resultSet.getInt(GOOD_ID) == good.getId() && resultSet.getLong(REVIEW_ID) != 0) {
            good.getReviews().add(mapReview(resultSet));
        }

        resultSet.next();
        return Optional.empty();
    }
}
