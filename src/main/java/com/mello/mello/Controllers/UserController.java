package com.mello.mello.Controllers;

import com.mello.mello.Model.User;
import com.mello.mello.Services.Implementation.ItemServiceImpl;
import com.mello.mello.Services.Service.LoginService;
import com.mello.mello.Services.Service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/users/{username}")
    public String routeToProfile(@PathVariable String username, HttpSession session, Model model) {

        // Get the user from the session
        User user = (User) session.getAttribute("user");

        // If the user is not null
        if (user != null) {
            // If the username is the current user's or the username deosn't exist
            if (!userService.existsByUsername(username)) {
                // Display the current user's profile
                return "profile";
            }
            // Display the user requested
            else {
                model.addAttribute("req_user", userService.findUserByUsername(username));
                return "user";
            }
        }

        // User must be logged in to view profiles
        else {
            model.addAttribute("message", "Please sign in to view user profiles.");
            return "login";
        }
    }

}
