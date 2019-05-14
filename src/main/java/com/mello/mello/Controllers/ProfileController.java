package com.mello.mello.Controllers;

import com.mello.mello.Model.Item;
import com.mello.mello.Model.Offer;
import com.mello.mello.Model.User;
import com.mello.mello.Model.UserLogin;
import com.mello.mello.Services.Service.ItemService;
import com.mello.mello.Services.Service.LoginService;
import com.mello.mello.Services.Service.OfferService;
import com.mello.mello.Services.Service.UserService;
import com.mello.mello.Util.Hash;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class ProfileController {

    private ItemService itemService;
    private LoginService loginService;
    private OfferService offerService;
    private UserService userService;


    //=========== PROFILE ===========//
    @GetMapping("/profile")
    public String displayProfile(HttpSession session){
        return session.getAttribute("user") == null ? "redirect:/login" : "profile";
    }


    @PostMapping("/profile/update/info")
    public String updateUserProfile(HttpServletRequest request) {

        boolean emailInUse = false;
        boolean usernameInUse = false;

        // Get the User from the session
        User user = (User) request.getSession().getAttribute("user");

        // Set the new names
        user.setFirstName(request.getParameter("f_name"));
        user.setLastName(request.getParameter("l_name"));
        user.setLocation(request.getParameter("location"));
        user.setImageUrl(request.getParameter("user-img-url"));

        // Persist new names in session and DB
        request.getSession().setAttribute("user", user);
        userService.saveUser(user);

        // Get the email
        String email = request.getParameter("email");
        String username = request.getParameter("username");

        // If the user has changed the email and it is not available, display error
        if (!user.getUserLogin().getEmail().equals(email) && loginService.emailAlreadyInUse(email)) {
            request.setAttribute("message", "Sorry, that email is already taken.");
            request.setAttribute("email", email);
            emailInUse = true;
        }

        // If the user has changed the username and it is not available, display error
        else if (!user.getUserLogin().getUsername().equals(username) && loginService.usernameAlreadyInUse(username)) {
            request.setAttribute("message", "Sorry, that username is already taken.");
            request.setAttribute("username", username);
            usernameInUse = true;
        }

        // Update the email and persist it in the session and DB
        if (!emailInUse) {
            user.getUserLogin().setEmail(email);
            request.getSession().setAttribute("user", user);
            loginService.saveUserLogin(user.getUserLogin());
        }

        if (!usernameInUse) {
            user.getUserLogin().setUsername(username);
            request.getSession().setAttribute("user", user);
            loginService.saveUserLogin(user.getUserLogin());
        }

        // If either the username or email was already in use, display error, otherwise redirect to profile
        return (usernameInUse || emailInUse) ? "profile" : "redirect:/profile";
    }


    @PostMapping("/profile/update/password")
    public String updateUserPassword(HttpServletRequest request, Model model, RedirectAttributes redirect) throws NoSuchAlgorithmException {

        // Get the User from the session
        User user = (User) request.getSession().getAttribute("user");

        // Verify that the user has entered the correct password
        boolean passwordIsCorrect = loginService.validateUserByUsername(user.getUsername(), request.getParameter("curr_password"));

        // If the password is correct
        if (passwordIsCorrect) {

            // And the new password field matches the confirm password field
            if (request.getParameter("new_password").equals(request.getParameter("conf_new_pass"))) {

                // Get the login object
                UserLogin login = loginService.findUserLoginByUsername(user.getUsername());

                // Create the new password and save it to the DB
                login.setPassword(request.getParameter("new_password"));
                loginService.saveUserLogin(login);

                // Redirect to profile
                redirect.addFlashAttribute("message", "Password changed successfully!");
                redirect.addFlashAttribute("success", true);
                return "redirect:/profile";
            }

            // The new pass and the confirm pass don't match
            redirect.addFlashAttribute("message", "New password fields don't match.");
            return "redirect:/profile";
        }

        // The user entered the wrong password
        redirect.addFlashAttribute("message", "You entered the wrong password.");
        return "redirect:/profile";

    }



    //=========== OFFERS ===========//
    @GetMapping("/offers")
    public String routeToOffers(HttpSession session) {

        // If the user is null, redirect to login
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        // Set any received offers to viewed
        for (Offer o : offerService.getCurrentReceivedOffersByUserId(user.getId())) {
            if (o.getMoment_viewed() == null) {
                o.setMoment_viewed(LocalDateTime.now());
                offerService.saveOffer(o);
            }
        }

        // Update the user's offers
        user.setReceived(offerService.getCurrentReceivedOffersByUserId(user.getId()));
        user.setSent(offerService.getCurrentSentOffersByUserId(user.getId()));

        // Send the offer lists to the page
        session.setAttribute(Offer.ACCEPTED, offerService.getAcceptedByUserId(user.getId()));
        session.setAttribute(Offer.REJECTED, offerService.getRejectedByUserId(user.getId()));
        session.setAttribute(Offer.WITHDRAWN, offerService.getWithdrawnByUserId(user.getId()));

        // Update the user in the session
        session.setAttribute("user", user);

        return "offers";
    }


    @PostMapping("/offers/new")
    public String newOffer(HttpServletRequest request, HttpSession session, Model model) {

        // Get the current user and the receiver user
        User user = (User) session.getAttribute("user");
        User receiver = userService.findUserById(request.getParameter("receiver_id"));

        // Get the receiver item id
        int receiverItemId = Integer.parseInt(request.getParameter("receiver_item"));

        // Check to make sure the user actually selected an item to trade before getting that id
        if (request.getParameter("sender_item") == null) {
            // Send an error message back to the item page they were on
            model.addAttribute("message", "You must select an item to make an offer.");
            return "redirect:/users/"+receiver.getUsername()+"/items/"+itemService.getItemById(receiverItemId).getUrlValue();
        }

        // Get the item being offered
        int senderItemId = Integer.parseInt(request.getParameter("sender_item"));

        // Make sure the items involved actually belong to the users
        if (itemService.itemBelongsToUser(senderItemId, user.getId())
            && itemService.itemBelongsToUser(receiverItemId, receiver.getId())
            && offerService.offerDoesNotAlreadyExist(senderItemId, receiverItemId)) {

            Offer offer = new Offer(user, receiver, senderItemId, receiverItemId);

            // Save to the db
            offerService.saveOffer(offer);
        }
        return "redirect:/offers";
    }


    @PostMapping("/offers/{id}/{action}")
    public String acceptRejectWithdraw(@PathVariable int id, @PathVariable String action, HttpSession session, RedirectAttributes redirect) {

        LoggerFactory.getLogger(ProfileController.class).info("//=== ACTION: " + action);

        // Make sure the user is not null
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        // Make sure the offer pertains to the user
        if (offerService.currentOfferBelongsToUser(user.getId(), id)) {

            // Get the offer object
            Offer offer = user.getOfferById(id);
            offer.setOfferClosed(true);
            offer.setOfferClosedDate(LocalDateTime.now());

            switch (action) {
                case "accept":
                    LoggerFactory.getLogger(ProfileController.class).info("//=== ACCEPTED");
                    offerService.closeOffer(offer, Offer.ACCEPTED);
                    redirect.addFlashAttribute("show_history", true);
                    break;
                case "decline":
                    LoggerFactory.getLogger(ProfileController.class).info("//=== DECLINED");
                    offer.setRejected(true);
                    redirect.addFlashAttribute("show_history", true);
                    redirect.addFlashAttribute("show_rejected", true);
                    break;
                case "withdraw":
                    LoggerFactory.getLogger(ProfileController.class).info("//=== WITHDRAWN");
                    offer.setWithdrawn(true);
                    redirect.addFlashAttribute("show_history", true);
                    redirect.addFlashAttribute("show_withdrawn", true);
                    break;
            }

            // Save to the db
            offerService.saveOffer(offer);

        }
        return "redirect:/offers";
    }


    @PostMapping("/offers/{id}/undo/{action}")
    public String undoAction(@PathVariable int id, @PathVariable String action, HttpSession session, RedirectAttributes redirect) {

        LoggerFactory.getLogger(ProfileController.class).info("//=== UNDO ACTION: " + action);

        // Make sure the user is not null
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        // Get the offer object
        Offer offer = offerService.getOfferById(id);
        LoggerFactory.getLogger(ProfileController.class).info("/-- Passed id: " + id);
        LoggerFactory.getLogger(ProfileController.class).info("/-- Offer id: " + offer.getId());
        LoggerFactory.getLogger(ProfileController.class).info("/-- Offer belongs to user id: " + Boolean.toString(offerService.closedOfferBelongsToUser(user.getId(), id)));
        LoggerFactory.getLogger(ProfileController.class).info("/-- Action can be undone: " + Boolean.toString(offer.actionCanBeUndone()));

        // Make sure the offer pertains to the user
        if (offerService.closedOfferBelongsToUser(user.getId(), id) && offer.actionCanBeUndone()) {

            // Set closed to false and reset the closed date to null
            offer.setOfferClosed(false);
            offer.setOfferClosedDate(null);

            switch (action) {
                case "decline":
                    LoggerFactory.getLogger(ProfileController.class).info("//=== UNDO DELETE");
                    offer.setRejected(false);
                    redirect.addFlashAttribute("show_received", true);
                    break;
                case "withdraw":
                    LoggerFactory.getLogger(ProfileController.class).info("//=== UNDO WITHDRAWN");
                    offer.setWithdrawn(false);
                    break;
            }

            // Save to the db
            offerService.saveOffer(offer);

        }

        return "redirect:/offers";
    }
}
