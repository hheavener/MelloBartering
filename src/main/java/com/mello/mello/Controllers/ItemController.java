package com.mello.mello.Controllers;

import com.mello.mello.Model.Item;
import com.mello.mello.Model.User;
import com.mello.mello.Services.Service.ItemService;
import com.mello.mello.Services.Service.OfferService;
import com.mello.mello.Services.Service.UserService;
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
import java.time.LocalDateTime;

@Controller
@AllArgsConstructor
public class ItemController {

    private ItemService itemService;
    private OfferService offerService;
    private UserService userService;

    // Viewing an specific item
    @GetMapping("users/{username}/items/{urlValue}")
    public String displayItem(@PathVariable String username, @PathVariable String urlValue, Model model){
        User user = userService.findUserByUsername(username);
        if (user != null) {
            Item item = itemService.getItemByOwnerUsernameAndUrlValue(user, urlValue);
            if (item != null) {
                model.addAttribute("item", item);
                return "item";
            }
            return "redirect:/users/" + username;
        }
        return "redirect:/profile";
    }

    // Display add item section on profile page
    @GetMapping("/users/{username}/items/new")
    public String displayNewItemSection(RedirectAttributes redirect) {
        redirect.addFlashAttribute("show_add_item", true);
        return "redirect:/profile#my_items";
    }

    // Add a new item
    @PostMapping("/users/{username}/items/add")
    public String addNewItem(@PathVariable String username, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        return saveOrCreateItem(username, "", session, request, redirectAttributes, true);
    }

    // Update an item
    @PostMapping("users/{username}/items/{urlValue}/save")
    public String updateItem(@PathVariable String username, @PathVariable String urlValue, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes){
        return saveOrCreateItem(username, urlValue, session, request, redirectAttributes, false);
    }

    // Edit an item
    @PostMapping("users/{username}/items/{urlValue}/edit")
    public String editItem(@PathVariable String urlValue, HttpSession session, RedirectAttributes redirect){
        if (session.getAttribute("user") == null) return "redirect:/login";

        // Get the user from the session
        User user = (User) session.getAttribute("user");

        // Get the item
        Item item = itemService.getItemByOwnerUsernameAndUrlValue(user, urlValue);

        // If the item belongs to the user, perform the action
        if (itemService.itemBelongsToUser(item.getId(), user.getId())) {
            redirect.addFlashAttribute("item", item);
            redirect.addFlashAttribute("edit_item", true);

        }

        // Redirect to profile
        return "redirect:/profile#my_items";

    }

    // Deleting an item
    @PostMapping("users/{username}/items/{urlValue}/delete")
    public String deleteItem(@PathVariable String urlValue, HttpSession session, RedirectAttributes redirect, boolean permanentlyDelete){
        if (session.getAttribute("user") == null) return "redirect:/login";

        // Get the user from the session
        User user = (User) session.getAttribute("user");

        // Get the item
        Item item = itemService.getItemByOwnerUsernameAndUrlValue(user, urlValue);

        // If the item belongs to the user, perform the action
        if (itemService.itemBelongsToUser(item.getId(), user.getId())) {

            if (permanentlyDelete) {
                offerService.deleteAllOffersContainingItem(item.getId());
                itemService.removeItem(item);
            }
            else {
                item.setAvailable(false);
                item.setDeletedByUser(true);
                itemService.saveItem(item);
                offerService.closeOffersContainingUserAndItem(user.getId(), item.getId());
                redirect.addFlashAttribute("show_unavailable", true);
            }
        }

        // Close any offers that had been made with this item
        offerService.closeOffersContainingUserAndItem(user.getId(), item.getId());

        // Refresh the user's list
        user.setOwnedItems(itemService.getAvailableItemsByUserId(user.getId()));
        session.setAttribute("user", user);

        // Return to the profile
        return "redirect:/profile#my_items";
    }

    // Deleting an item
    @PostMapping("users/{username}/items/{urlValue}/perm-delete")
    public String permanentlyDelete(@PathVariable String urlValue, HttpSession session, RedirectAttributes redirectAttributes) {
        return deleteItem(urlValue, session, redirectAttributes, true);
    }

    // Deleting an item
    @PostMapping("users/{username}/items/{urlValue}/undo-delete")
    public String undoDeleteItem(@PathVariable String urlValue, HttpSession session, RedirectAttributes redirect){
        if (session.getAttribute("user") == null) return "redirect:/login";

        // Get the user from the session
        User user = (User) session.getAttribute("user");

        // Get the item
        Item item = itemService.getItemByOwnerUsernameAndUrlValue(user, urlValue);

        // If the item belongs to the user, perform the action
        if (itemService.itemBelongsToUser(item.getId(), user.getId())) {
            item.setAvailable(true);
            item.setDeletedByUser(false);
            itemService.saveItem(item);
        }
        // Refresh the user's list
        user.setOwnedItems(itemService.getAvailableItemsByUserId(user.getId()));
        session.setAttribute("user", user);

        // Return to the profile
        return "redirect:/profile#my_items";
    }



    // ========= Unrouted methods ========= //
    private String saveOrCreateItem(String username, String urlValue, HttpSession session, HttpServletRequest request, RedirectAttributes redirect, boolean create) {

        LoggerFactory.getLogger(ItemController.class).info("//=== IC.saveOrCreateItem: Create: " + create);

        // Get the user from the session
        User user = (User) session.getAttribute("user");

        // If the user is null redirect to login
        if (user == null) return "redirect:/login";
        // If the user's username does not match, redirect to profile
        if (!user.getUsername().equals(username)) return "redirect:/profile";

        // Assume all the information is not present
        boolean allInfoObtained = false;

        // Get the item information
        String name = request.getParameter("name");
        String condition = request.getParameter("condition");
        String categories = request.getParameter("categories");
        String imageUrl = request.getParameter("image_url");

        Item item;

        // If adding new item, create an item to send back any information the user entered if below conditions fail
        if (create) item = new Item(name, condition, categories, imageUrl, LocalDateTime.now(), user);
            // Otherwise, get the item from the database
        else item = itemService.getItemByOwnerUsernameAndUrlValue(user, urlValue);

        // Check each field to see if it is null or empty
        if (name == null || name.isEmpty()) {
            redirect.addFlashAttribute("item_error", "The name field cannot be blank");
        } else if (condition == null || condition.isEmpty()) {
            redirect.addFlashAttribute("item_error", "The condition cannot be blank");
        } else if (categories == null || categories.isEmpty()) {
            redirect.addFlashAttribute("item_error", "The categories field cannot be blank");
        } else if (imageUrl == null || imageUrl.isEmpty()) {
            redirect.addFlashAttribute("item_error", "The image field cannot be blank");
        } else {
            allInfoObtained = true;
        }

        // All information was provided, so persist the data
        if (allInfoObtained) {
            if(!create) item = itemService.getItemByOwnerUsernameAndUrlValue(user, urlValue);
            item.setName(name);
            item.setItem_condition(condition);
            item.setCategories(categories);
            itemService.saveItem(item);
            user.addOwnedItem(item);
            session.setAttribute("user", user);
        }

        // If upload failed, display add item section when page reloads
        else {
            redirect.addFlashAttribute("item", item);
            if (!create) redirect.addFlashAttribute("edit_item", true);
            else redirect.addFlashAttribute("show_add_item", true);
        }

        return "redirect:/profile#my_items";
    }
}
