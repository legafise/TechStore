package by.lashkevich.logic.dao.impl;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.UserDao;
import by.lashkevich.logic.dao.mapper.DaoMapper;
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
    private static final String REMOVE_USER_BY_ID_SQL = "DELETE FROM users WHERE id = ?";
    private static final String CREATE_USER_SQL = "INSERT INTO users (name, surname, login, password, email," +
            " profile_picture, birth_date, balance, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_SQL = "UPDATE users SET name = ?, surname = ?, login = ?, password = ?," +
            " email = ?, profile_picture = ?, birth_date = ?, balance = ?, role = ? WHERE id = ?";
    private final DaoMapper daoMapper;
    private Connection connection;

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public JWDUserDao() {
        daoMapper = new DaoMapper();
    }

    @Override
    public List<User> findAll() throws DaoException {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS_SQL);
            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(daoMapper.mapUser(resultSet));
            }

            return users;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findById(Long id) throws DaoException {
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID_SQL);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            User user = new User();

            while (resultSet.next()) {
                user = daoMapper.mapUser(resultSet);
            }

            return user.getId() != 0 ? Optional.of(user) : Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public boolean add(User user) throws DaoException {
        try {
            PreparedStatement statement = connection.prepareStatement(CREATE_USER_SQL);
            fillUserData(user, statement);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public boolean removeById(Long id) throws DaoException {
        try {
            PreparedStatement statement = connection.prepareStatement(REMOVE_USER_BY_ID_SQL);
            statement.setLong(1, id);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public boolean update(User user) throws DaoException {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER_SQL);
            statement.setLong(10, user.getId());
            fillUserData(user, statement);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private void fillUserData(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getName());
        statement.setString(2, user.getSurname());
        statement.setString(3, user.getLogin());
        statement.setString(4, user.getPassword());
        statement.setString(5, user.getEmail());
        statement.setString(6, user.getProfilePicture());
        statement.setDate(7, Date.valueOf(user.getBirthDate()));
        statement.setBigDecimal(8, user.getBalance());
        statement.setInt(9, user.getRole().getRoleNumber());
    }
}
