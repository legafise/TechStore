package by.lashkevich.logic.service.validator;

import by.lashkevich.logic.entity.User;
import by.lashkevich.logic.service.checker.FileFormatChecker;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * The type User validator.
 * @author Roman Lashkevich
 */
public class UserValidator {
    private FileFormatChecker formatChecker;
    private static final String EMAIL_PATTERN = "^([а-яa-z0-9_-]+\\.)*[а-яa-z0-9_-]+@[а-яa-z0-9_-]" +
            "+(\\.[а-яa-z0-9_-]+)*\\.[а-яa-z]{2,6}$";

    public UserValidator() {
        formatChecker = new FileFormatChecker();
    }

    public void setFormatChecker(FileFormatChecker formatChecker) {
        this.formatChecker = formatChecker;
    }

    public boolean validate(User user) {
        return user != null && validateName(user.getName()) && validateSurname(user.getSurname())
                && validateLogin(user.getLogin()) && validatePassword(user.getPassword())
                && validateBirthDate(user.getBirthDate()) && validateEmail(user.getEmail())
                && user.getRole() != null && validateProfilePicture(user.getProfilePictureName());
    }

    private boolean validateProfilePicture(String profilePictureName) {
        return profilePictureName != null && formatChecker.checkImgFormat(profilePictureName);
    }

    private boolean validateBirthDate(LocalDate birthDate) {
        return birthDate != null && birthDate.getYear() <= 2007 && birthDate.getYear() >= 1921;
    }

    private boolean validateEmail(String email) {
        return email != null && email.length() <= 45 && Pattern.matches(EMAIL_PATTERN, email);
    }

    private boolean validateLogin(String login) {
        return login != null && login.length() >= 2 && login.length() <= 35;
    }

    private boolean validateSurname(String surname) {
        return surname != null && surname.length() >= 2 && surname.length() <= 30;
    }

    private boolean validateName(String name) {
        return name != null && name.length() >= 2 && name.length() <= 30;
    }

    private boolean validatePassword(String password) {
        return password != null && password.length() >= 4 && password.length() <= 60;
    }
}
