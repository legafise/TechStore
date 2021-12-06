package by.lashkevich.logic.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * The type User.
 * @author Roman Lashkevich
 */
public class User implements Entity {
    private long id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String email;
    private LocalDate birthDate;
    private String profilePictureName;
    private BigDecimal balance;
    private Role role;

    /**
     * Instantiates a new User.
     */
    public User() {
        profilePictureName = "";
    }

    /**
     * Instantiates a new User.
     *
     * @param id                 the id
     * @param name               the name
     * @param surname            the surname
     * @param login              the login
     * @param password           the password
     * @param email              the email
     * @param birthDate          the birth date
     * @param profilePictureName the profile picture name
     * @param balance            the balance
     * @param role               the role
     */
    public User(long id, String name, String surname, String login, String password, String email, LocalDate birthDate,
                String profilePictureName, BigDecimal balance, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.profilePictureName = profilePictureName;
        this.balance = balance;
        this.role = role;
    }

    /**
     * Instantiates a new User.
     *
     * @param name               the name
     * @param surname            the surname
     * @param login              the login
     * @param password           the password
     * @param email              the email
     * @param birthDate          the birth date
     * @param profilePictureName the profile picture name
     */
    public User(String name, String surname, String login, String password, String email, LocalDate birthDate,
                String profilePictureName) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.profilePictureName = profilePictureName;
    }

    /**
     * Instantiates a new User.
     *
     * @param name               the name
     * @param surname            the surname
     * @param login              the login
     * @param password           the password
     * @param email              the email
     * @param birthDate          the birth date
     * @param profilePictureName the profile picture name
     * @param balance            the balance
     * @param role               the role
     */
    public User(String name, String surname, String login, String password, String email, LocalDate birthDate,
                String profilePictureName, BigDecimal balance, Role role) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.profilePictureName = profilePictureName;
        this.balance = balance;
        this.role = role;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets surname.
     *
     * @param surname the surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets login.
     *
     * @param login the login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets birth date.
     *
     * @return the birth date
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Sets birth date.
     *
     * @param birthDate the birth date
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Gets profile picture name.
     *
     * @return the profile picture name
     */
    public String getProfilePictureName() {
        return profilePictureName;
    }

    /**
     * Sets profile picture name.
     *
     * @param profilePictureName the profile picture name
     */
    public void setProfilePictureName(String profilePictureName) {
        this.profilePictureName = profilePictureName;
    }

    /**
     * Gets balance.
     *
     * @return the balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets balance.
     *
     * @param balance the balance
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(birthDate, user.birthDate) &&
                Objects.equals(profilePictureName, user.profilePictureName) &&
                Objects.equals(balance, user.balance) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, login, password, email, birthDate,
                profilePictureName, balance, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", profilePicture='" + profilePictureName + '\'' +
                ", balance=" + balance +
                ", role=" + role +
                '}';
    }
}