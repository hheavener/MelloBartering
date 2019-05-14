package com.mello.mello.Controllers;

import com.mello.mello.Model.User;
import com.mello.mello.Model.UserLogin;
import com.mello.mello.Services.Service.LoginService;
import com.mello.mello.Services.Service.UserService;
import com.mello.mello.Util.Hash;
import com.mello.mello.Util.IdGenerator;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;


@Controller
@AllArgsConstructor
public class RegistrationController {

    private final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    private UserService userService;
    private LoginService loginService;

    //========== REGISTER ==========//
    private boolean register(HttpServletRequest request) throws NoSuchAlgorithmException {

        // These will be returned to the user if registration fails
        request.setAttribute("f_name", request.getParameter("f_name"));
        request.setAttribute("l_name", request.getParameter("l_name"));
        request.setAttribute("email", request.getParameter("email"));
        request.setAttribute("username", request.getParameter("username"));

        // Make sure the email is not already in use
        if (loginService.emailAlreadyInUse(request.getParameter("email"))) {
            error(request, "Sorry, that email address in already in use.");
        } else if (loginService.usernameAlreadyInUse(request.getParameter("username"))) {
            error(request, "Sorry, that username in already in use.");
        }

        // Make sure the passwords are not mismatched
        else if (!request.getParameter("password").equals(request.getParameter("conf_pass"))) {
            error(request, "Passwords do not match.");
        }

        else {
            // Create a new User object
            User user = new User();
            user.setId(IdGenerator.getUniqueUserId(userService));
            user.setFirstName(request.getParameter("f_name"));
            user.setLastName(request.getParameter("l_name"));
            user.setLocation(request.getParameter("location"));

            UserLogin userLogin = new UserLogin(user);
            userLogin.setEmail(request.getParameter("email"));
            userLogin.setUsername(request.getParameter("username"));
            userLogin.setPassword(request.getParameter("password"));

            LoggerFactory.getLogger(RegistrationController.class).info("//=== UserLogin password: " + userLogin.getPassword());

            // Associate the UserLogin with the user
            user.setUserLogin(userLogin);

            // Save the user to the database
            userService.saveUser(user);
            loginService.saveUserLogin(userLogin);

            // Set the password to null for session purposes
            userLogin.setPassword(null);

            // Create a new session and add the user to it
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);

            // Registration was successful
            return true;
        }

        // When the user registration fails
        return false;
    }



    //========== ERROR MESSAGING ==========//
    public String error(HttpServletRequest request, String message) {
        request.setAttribute("message", message);
        return "registration";
    }



    //========== URL MAPPINGS ==========//
    @PostMapping("/register")
    private String defaultRegister (HttpServletRequest request, HttpSession session) throws NoSuchAlgorithmException {
        // Make sure the user is not already logged in
        if (session.getAttribute("user") != null) return "redirect:/profile";

            // If the registration is successful
        else if (register(request)) {

            // See if there is a return url
            String returnUrl = (String) session.getAttribute("returnUrl");
            session.removeAttribute("returnUrl");

            // If there is, go there, if not go to 'my saved' page
            return returnUrl != null ? "redirect:/" + returnUrl : "redirect:/profile";
        }

        // If the login is not successful, return an error
        else return "registration";
    }


    @GetMapping("/register")
    public String showRegisterPage(HttpSession session) {
        return session.getAttribute("user") == null ? "registration" : "profile";
    }


}
