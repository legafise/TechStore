package by.lashkevich.web.controller.command.impl;

import by.lashkevich.logic.entity.Role;
import by.lashkevich.logic.entity.User;
import by.lashkevich.logic.service.ServiceFactory;
import by.lashkevich.logic.service.UserService;
import by.lashkevich.web.controller.command.Command;
import by.lashkevich.web.controller.command.CommandException;
import by.lashkevich.web.controller.command.CommandResult;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * The type Registration command.
 * @author Roman Lashkevich
 * @see Command
 */
public class RegistrationCommand implements Command {
    private final UserService userService;

    /**
     * Instantiates a new Registration command.
     */
    public RegistrationCommand() {
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        try {
            if (!validateUserData(request)) {
                request.getSession().setAttribute("registrationResult", false);
                return new CommandResult(CommandResult.ResponseType.REDIRECT,
                        "/controller?command=check_registration");
            }

            User user = mapUser(request);

            for (Part part : request.getParts()) {
                if (part.getName().equals("profilePhoto")) {
                    InputStream inputStream = part.getInputStream();
                    InputStreamReader isr = new InputStreamReader(inputStream);
                    new BufferedReader(isr)
                            .lines()
                            .collect(Collectors.joining("\n"));
                    if (!part.getSubmittedFileName().isEmpty()) {
                        String pictureName = user.getLogin() + "_" + part.getSubmittedFileName();
                        user.setProfilePictureName(pictureName);
                        pictureName = "users_" + user.getLogin() + "_" + part.getSubmittedFileName();
                        part.write(pictureName);
                    }
                }
            }

            request.getSession().setAttribute("registrationResult", userService.addUser(user));
            return new CommandResult(CommandResult.ResponseType.REDIRECT,
                    "/controller?command=check_registration");
        } catch (IOException | ServletException e) {
            throw new CommandException(e);
        }
    }

    private boolean validateUserData(HttpServletRequest request) {
        String login = request.getParameter("login");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String birthDate = request.getParameter("birthDate");

        return login != null && name != null && surname != null && email != null && password != null
                && birthDate != null && !login.isEmpty() && !name.isEmpty() && !surname.isEmpty() && !email.isEmpty()
                && !password.isEmpty() && !birthDate.isEmpty();
    }

    private User mapUser(HttpServletRequest request) {
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setName(request.getParameter("name"));
        user.setSurname(request.getParameter("surname"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setRole(Role.USER);
        user.setBirthDate(LocalDate.parse(request.getParameter("birthDate")));
        return user;
    }
}
