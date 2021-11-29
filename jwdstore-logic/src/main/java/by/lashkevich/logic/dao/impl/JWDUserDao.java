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

public class JWDUserDao implements UserDao {
    private static final String FIND_ALL_USERS_SQL = "SELECT users.id AS user_id, users.name AS user_name," +
            " users.surname AS user_surname, users.login AS user_login, users.password AS user_password, users.email" +
            " AS user_email, users.profile_picture AS user_profile_picture, users.birth_date AS user_birth_date," +
            " users.balance AS user_balance, users.role AS user_role FROM users";
    private static final String FIND_USER_BY_ID_SQL = "SELECT users.id AS user_id, users.name AS user_name," +
            " users.surname AS user_surname, users.login AS user_login, users.password AS user_password, users.email" +
            " AS user_email, users.profile_picture AS user_profile_picture, users.birth_date AS user_birth_date," +
            " users.balance AS user_balance, users.role AS user_role FROM users WHERE id = ?";
    private static final String FIND_USER_BY_EMAIL_SQL = "SELECT users.id AS user_id, users.name AS user_name," +
            " users.surname AS user_surname, users.login AS user_login, users.password AS user_password, users.email" +
            " AS user_email, users.profile_picture AS user_profile_picture, users.birth_date AS user_birth_date," +
            " users.balance AS user_balance, users.role AS user_role FROM users WHERE email = ?";
    private static final String FIND_USER_BY_LOGIN_SQL = "SELECT users.id AS user_id, users.name AS user_name," +
            " users.surname AS user_surname, users.login AS user_login, users.password AS user_password, users.email" +
            " AS user_email, users.profile_picture AS user_profile_picture, users.birth_date AS user_birth_date," +
            " users.balance AS user_balance, users.role AS user_role FROM users WHERE login = ?";
    private static final String REMOVE_USER_BY_ID_SQL = "DELETE FROM users WHERE id = ?";
    private static final String REMOVE_BASKET_BY_USER_ID_SQL = "DELETE FROM baskets WHERE user_id = ?";
    private static final String CREATE_USER_SQL = "INSERT INTO users (name, surname, login, password, email," +
            " profile_picture, birth_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_SQL = "UPDATE users SET name = ?, surname = ?, login = ?, password = ?," +
            " email = ?, profile_picture = ?, birth_date = ?, balance = ?, role = ? WHERE id = ?";
    private static final String FIND_BASKET_BY_USER_ID_SQL = "SELECT baskets_owners.id AS user_id, baskets_owners.name" +
            " AS user_name, baskets_owners.surname AS user_surname, baskets_owners.login AS user_login," +
            " baskets_owners.password AS user_password, baskets_owners.email AS user_email," +
            " baskets_owners.profile_picture AS user_profile_picture, baskets_owners.birth_date AS user_birth_date," +
            " baskets_owners.balance AS user_balance, baskets_owners.role AS user_role, baskets.good_id AS good_id," +
            " baskets.quantity AS good_quantity, goods.name AS good_name, goods.price AS good_price, goods.description" +
            " AS good_description, goods.picture AS good_picture, goods_types.name AS good_type_name, reviews.id" +
            " AS review_id, reviews.rate AS review_rate, reviews.content AS review_content, users.id AS review_user_id," +
            " users.name AS review_user_name, users.surname AS review_user_surname, users.login AS review_user_login," +
            " users.password AS review_user_password, users.email AS review_user_email, users.profile_picture AS" +
            " review_user_profile_picture, users.birth_date AS review_user_birth_date,users.balance AS" +
            " review_user_balance, users.role AS review_user_role FROM users as baskets_owners LEFT JOIN baskets" +
            " ON baskets.user_id = baskets_owners.id LEFT JOIN goods ON baskets.good_id = goods.id LEFT JOIN" +
            " goods_types ON goods.type_id = goods_types.id LEFT JOIN goods_reviews ON goods.id =" +
            " goods_reviews.good_id LEFT JOIN reviews ON goods_reviews.review_id = reviews.id LEFT JOIN users ON" +
            " users.id = reviews.user_id WHERE baskets_owners.id = ? ORDER BY baskets.good_id";
    private final DaoMapper daoMapper;

    public JWDUserDao() {
        daoMapper = new DaoMapper();
    }

    @Override
    public List<User> findAll() throws DaoException {
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
    public Optional<User> findById(Long id) throws DaoException {
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
    public boolean add(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER_SQL)) {
            fillAddingUserData(user, statement);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean removeById(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_USER_BY_ID_SQL)) {
            statement.setLong(1, id);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(User user) throws DaoException {
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
    public Optional<Basket> findBasketByUserId(Long userId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BASKET_BY_USER_ID_SQL)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            Basket basket = new Basket();

            while (!resultSet.isAfterLast()) {
                basket = daoMapper.mapBasket(resultSet);
            }

            return Optional.of(basket);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) throws DaoException {
        return findUserByStringParameter(email, FIND_USER_BY_EMAIL_SQL);
    }

    @Override
    public boolean clearBasketByUserId(Long userId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_BASKET_BY_USER_ID_SQL)) {
            preparedStatement.setLong(1, userId);
            return preparedStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    private void fillUpdatingUserData(User user, PreparedStatement statement) throws SQLException {
        fillAddingUserData(user, statement);
        statement.setBigDecimal(8, user.getBalance());
        statement.setInt(9, user.getRole().getRoleNumber());
    }
    // TODO: 28.11.2021 Метод добавления \ метод изменения количества

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        return findUserByStringParameter(login, FIND_USER_BY_LOGIN_SQL);
    }

    private Optional<User> findUserByStringParameter(String parameter, String findSQLScript) throws DaoException {
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
