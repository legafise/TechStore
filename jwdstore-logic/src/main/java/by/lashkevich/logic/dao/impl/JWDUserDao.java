package by.lashkevich.logic.dao.impl;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.UserDao;
import by.lashkevich.logic.dao.mapper.DaoMapper;
import by.lashkevich.logic.dao.pool.ConnectionPool;
import by.lashkevich.logic.entity.Basket;
import by.lashkevich.logic.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Jwd user dao.
 * @author Roman Lashkevich
 * @see UserDao
 */
public class JWDUserDao implements UserDao {
    private static final String INVALID_GOOD_MESSAGE = "Invalid good";
    private static final String FIND_ALL_USERS_SQL = "SELECT users.id AS user_id, users.name AS user_name," +
            " users.surname, users.login, users.password, users.email, users.profile_picture, users.birth_date," +
            " users.balance, users.role FROM users";
    private static final String FIND_USER_BY_ID_SQL = "SELECT users.id AS user_id, users.name AS user_name," +
            " users.surname, users.login, users.password, users.email, users.profile_picture, users.birth_date," +
            " users.balance, users.role FROM users WHERE users.id = ?";
    private static final String FIND_USER_BY_EMAIL_SQL = "SELECT users.id AS user_id, users.name AS user_name," +
            " users.surname, users.login, users.password, users.email, users.profile_picture, users.birth_date," +
            " users.balance, users.role FROM users WHERE users.email = ?";
    private static final String FIND_USER_BY_LOGIN_SQL = "SELECT users.id AS user_id, users.name AS user_name," +
            " users.surname, users.login, users.password, users.email, users.profile_picture, users.birth_date," +
            " users.balance, users.role FROM users WHERE users.login = ?";
    private static final String REMOVE_USER_BY_ID_SQL = "DELETE FROM users WHERE id = ?";
    private static final String REMOVE_BASKET_BY_USER_ID_SQL = "DELETE FROM baskets WHERE user_id = ?";
    private static final String CREATE_USER_SQL = "INSERT INTO users (name, surname, login, password, email," +
            " profile_picture, birth_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_SQL = "UPDATE users SET name = ?, surname = ?, login = ?, password = ?," +
            " email = ?, profile_picture = ?, birth_date = ?, balance = ?, role = ? WHERE id = ?";
    private static final String ADD_GOOD_IN_BASKET_SQL = "INSERT INTO baskets (user_id, good_id) VALUES (?, ?)";
    private static final String REMOVE_GOOD_FROM_BASKET_SQL = "DELETE FROM baskets" +
            " WHERE baskets.user_id = ? AND baskets.good_id = ?";
    private static final String FIND_BASKET_BY_USER_ID_SQL = "SELECT baskets_owners.id AS user_id, baskets_owners.name AS user_name," +
            " baskets_owners.surname, baskets_owners.login, baskets_owners.password, baskets_owners.email," +
            " baskets_owners.profile_picture, baskets_owners.birth_date," +
            " baskets_owners.balance, baskets_owners.role, baskets.good_id AS good_id," +
            " baskets.quantity, goods.name AS good_name, goods.price AS good_price, goods.description, goods.picture," +
            " goods_types.name AS good_type_name, goods_types.id AS good_type_id, reviews.id" +
            " AS review_id, reviews.rate, reviews.content, users.id AS review_user_id," +
            " users.name AS review_user_name, users.surname AS review_user_surname, users.login AS review_user_login," +
            " users.password AS review_user_password, users.email AS review_user_email, users.profile_picture AS" +
            " review_user_profile_picture, users.birth_date AS review_user_birth_date, users.balance AS" +
            " review_user_balance, users.role AS review_user_role FROM users as baskets_owners LEFT JOIN baskets" +
            " ON baskets.user_id = baskets_owners.id LEFT JOIN goods ON baskets.good_id = goods.id LEFT JOIN" +
            " goods_types ON goods.type_id = goods_types.id LEFT JOIN goods_reviews ON goods.id =" +
            " goods_reviews.good_id LEFT JOIN reviews ON goods_reviews.review_id = reviews.id LEFT JOIN users ON" +
            " users.id = reviews.user_id WHERE baskets_owners.id = ? ORDER BY baskets.good_id";
    private static final String CHANGE_GOOD_QUANTITY_IN_BASKET_SQL = "UPDATE baskets SET baskets.quantity = ?" +
            " WHERE baskets.user_id = ? AND baskets.good_id = ?";
    private final DaoMapper daoMapper;

    /**
     * Instantiates a new Jwd user dao.
     */
    public JWDUserDao() {
        daoMapper = new DaoMapper();
    }

    @Override
    public List<User> findAll() {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS_SQL);
            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(daoMapper.mapUser(resultSet));
            }

            return users;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id)  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID_SQL)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            User user = new User();

            while (resultSet.next()) {
                user = daoMapper.mapUser(resultSet);
            }

            return user.getId() != 0 ? Optional.of(user) : Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean add(User user)  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER_SQL)) {
            fillAddingUserData(user, statement);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean removeById(Long id)  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_USER_BY_ID_SQL)) {
            statement.setLong(1, id);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(User user)  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_SQL)) {
            statement.setLong(10, user.getId());
            fillUpdatingUserData(user, statement);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private void fillAddingUserData(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getName());
        statement.setString(2, user.getSurname());
        statement.setString(3, user.getLogin());
        statement.setString(4, user.getPassword());
        statement.setString(5, user.getEmail());
        statement.setString(6, user.getProfilePictureName());
        statement.setDate(7, Date.valueOf(user.getBirthDate()));
    }

    @Override
    public Optional<Basket> findBasketByUserId(Long userId)  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BASKET_BY_USER_ID_SQL)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            Basket basket = new Basket();

            while (!resultSet.isAfterLast()) {
                basket = daoMapper.mapBasket(resultSet);
            }

            return basket.getGoods().entrySet().stream().anyMatch(entry -> entry.getKey().getId() == 0)
                    ? Optional.empty() : Optional.of(basket);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email)  {
        return findUserByStringParameter(email, FIND_USER_BY_EMAIL_SQL);
    }

    @Override
    public boolean clearBasketByUserId(Long userId)  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_BASKET_BY_USER_ID_SQL)) {
            preparedStatement.setLong(1, userId);
            return preparedStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login)  {
        return findUserByStringParameter(login, FIND_USER_BY_LOGIN_SQL);
    }

    @Override
    public boolean addGoodInBasket(Long userId, Long goodId)  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_GOOD_IN_BASKET_SQL)) {
            statement.setLong(1, userId);
            statement.setLong(2, goodId);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(INVALID_GOOD_MESSAGE ,e);
        }
    }

    @Override
    public boolean removeGoodFromBasket(Long userId, Long goodId)  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_GOOD_FROM_BASKET_SQL)) {
            statement.setLong(1, userId);
            statement.setLong(2, goodId);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean changeGoodQuantity(Long userId, Long goodId, Short goodQuantity)  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(CHANGE_GOOD_QUANTITY_IN_BASKET_SQL)) {
            statement.setShort(1, goodQuantity);
            statement.setLong(2, userId);
            statement.setLong(3, goodId);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private void fillUpdatingUserData(User user, PreparedStatement statement) throws SQLException {
        fillAddingUserData(user, statement);
        statement.setBigDecimal(8, user.getBalance());
        statement.setInt(9, user.getRole().getNumber());

    }

    private Optional<User> findUserByStringParameter(String parameter, String findSQLScript)  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(findSQLScript)) {
            statement.setString(1, parameter);
            ResultSet resultSet = statement.executeQuery();
            User user = new User();

            while (resultSet.next()) {
                user = daoMapper.mapUser(resultSet);
            }

            return user.getId() != 0 ? Optional.of(user) : Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
