package by.lashkevich.logic.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class User implements Entity {
    private long id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String email;
    private LocalDate birthDate;
    private String profilePicture;
    private BigDecimal balance;
    private Role role;

    public User(long id, String name, String surname, String login, String password, String email, LocalDate birthDate,
                String profilePicture, BigDecimal balance, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.profilePicture = profilePicture;
        this.balance = balance;
        this.role = role;
    }

    public User(String name, String surname, String login, String password, String email, LocalDate birthDate,
                String profilePicture, BigDecimal balance, Role role) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.profilePicture = profilePicture;
        this.balance = balance;
        this.role = role;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Role getRole() {
        return role;
    }

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
                Objects.equals(profilePicture, user.profilePicture) &&
                Objects.equals(balance, user.balance) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, login, password, email, birthDate,
                profilePicture, balance, role);
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
                ", profilePicture='" + profilePicture + '\'' +
                ", balance=" + balance +
                ", role=" + role +
                '}';
    }
}