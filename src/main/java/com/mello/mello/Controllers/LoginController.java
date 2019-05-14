package com.mello.mello.Controllers;

import com.mello.mello.Model.User;
import com.mello.mello.Services.Service.ItemService;
import com.mello.mello.Services.Service.LoginService;
import com.mello.mello.Services.Service.OfferService;
import com.mello.mello.Services.Service.UserService;
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
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(LoginController.class);

    private UserService userService;
    private LoginService loginService;
    private ItemService itemService;
    private OfferService offerService;


    //========== LOGIN FUNCTION ==========//
    private boolean login(HttpServletRequest request) throws NoSuchAlgorithmException {

        String user_id = request.getParameter("user_id");

        boolean loginWithUsername = loginService.validateUserByUsername(user_id, request.getParameter("password"));
        boolean loginWithEmail = loginService.validateUserByEmail(user_id, request.getParameter("password"));

        if (loginWithUsername || loginWithEmail) {
            HttpSession session = request.getSession(true);
            User user = loginWithUsername ? userService.findUserByUsername(user_id) : userService.findUserByEmail(user_id);

            // Set all the users' lists
            user.setOwnedItems(itemService.getAvailableItemsByUserId(user.getId()));
            user.setSent(offerService.getCurrentSentOffersByUserId(user.getId()));
            user.setReceived(offerService.getCurrentReceivedOffersByUserId(user.getId()));

            session.setAttribute("user", user);
            return true;
        }

        request.setAttribute("user_id", user_id);
        return false;
    }



    //========== ERROR MESSAGING ==========//
    private String error(HttpServletRequest request) {
        request.setAttribute("message", "Sorry, that user ID and password is not valid.");
        return "login";
    }



    //========== URL MAPPINGS ==========//


    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        return session.getAttribute("user") == null ? "login" : "redirect:/profile";
    }

    @PostMapping("/login")
    public String defaultLogin(HttpServletRequest request, HttpSession session) throws NoSuchAlgorithmException {
        if (session.getAttribute("user") != null) return "redirect:/profile";

            // If the login is successful
        else if (login(request)) {

            // See if there is a return url
            String returnUrl = (String) session.getAttribute("returnUrl");
            session.removeAttribute("returnUrl");

            // If there is, go there, if not go to profile page
            return returnUrl != null ? "redirect:/" + returnUrl : "redirect:/profile";
        }

        // If the login is not successful, return an error
        else return error(request);
    }


    //========== LOGOUT ==========//
    @PostMapping("/logout")
    private String logout(HttpSession session) {
        // do logout stuff
        if (session != null) session.invalidate();
        return "redirect:/login";
    }

}
