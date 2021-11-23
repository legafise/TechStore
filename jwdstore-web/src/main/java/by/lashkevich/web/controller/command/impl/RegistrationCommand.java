package by.lashkevich.web.controller.command.impl;

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

public class RegistrationCommand implements Command {
    private final UserService userService;

    public RegistrationCommand() {
        userService = (UserService) ServiceFactory.USER_SERVICE.getService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            if (!registrationValidator(request)) {
                request.getSession().setAttribute("registrationResult", false);
                return new CommandResult(CommandResult.ResponseType.REDIRECT,
                        "/controller?command=check_registration");
            }

            User user = registrationMapper(request);

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

            request.getSession().setAttribute("registrationResult", userService.add(user));
            return new CommandResult(CommandResult.ResponseType.REDIRECT,
                    "/controller?command=check_registration");
        } catch (IOException | ServletException e) {
            throw new CommandException(e);
        }
    }

    private boolean registrationValidator(HttpServletRequest request) {
        return !request.getParameter("login").isEmpty() && !request.getParameter("name").isEmpty() && !request.getParameter("surname").isEmpty() &&
                !request.getParameter("email").isEmpty() && !request.getParameter("password").isEmpty() && !request.getParameter("birthDate").isEmpty();
    }

    private User registrationMapper(HttpServletRequest request) {
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setName(request.getParameter("name"));
        user.setSurname(request.getParameter("surname"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(BCrypt.hashpw(request.getParameter("password"), BCrypt.gensalt()));
        user.setBirthDate(LocalDate.parse(request.getParameter("birthDate")));
        return user;
    }
}
