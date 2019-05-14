package com.mello.mello.Controllers;

import com.mello.mello.Model.Item;
import com.mello.mello.Model.Offer;
import com.mello.mello.Model.User;
import com.mello.mello.Model.UserLogin;
import com.mello.mello.Services.Service.ItemService;
import com.mello.mello.Services.Service.LoginService;
import com.mello.mello.Services.Service.OfferService;
import com.mello.mello.Services.Service.UserService;
import com.mello.mello.Util.IdGenerator;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@AllArgsConstructor
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(TestController.class);
    private UserService userService;
    private ItemService itemService;
    private OfferService offerService;
    private LoginService loginService;

    @GetMapping("/init")
    public String initializeDB(HttpServletRequest request) throws NoSuchAlgorithmException {

        // Clear the database
        userService.deleteAllEntries();
        itemService.deleteAllEntries();
        offerService.deleteAllEntries();
        loginService.deleteAllEntries();

        // Create the first user
        User user1 = new User();
        user1.setId(IdGenerator.getUniqueUserId(userService));
        user1.setFirstName("Hunter");
        user1.setLastName("Heavener");
        user1.setLocation("Cramerton, NC");
        user1.setImageUrl("/www.mello.com/images/Hunter.png");

        UserLogin userLogin = new UserLogin(user1);
        userLogin.setEmail("hheavene@uncc.edu");
        userLogin.setUsername("hheavener");
        userLogin.setPassword("pass");
        user1.setUserLogin(userLogin);

        // Create the second user
        User user2 = new User();
        user2.setId(IdGenerator.getUniqueUserId(userService));
        user2.setFirstName("Emma");
        user2.setLastName("Heavener");
        user2.setLocation("Cramerton, NC");
        user2.setImageUrl("https://res.cloudinary.com/campus-job/image/upload/t_student-public-page/v1/profile_pictures/ebEyVWawOW_20171212.png");

        UserLogin userLogin2 = new UserLogin(user2);
        userLogin2.setEmail("estadick@uncc.edu");
        userLogin2.setUsername("estadick");
        userLogin2.setPassword("pass");
        user2.setUserLogin(userLogin2);

        // Create the third user
        User user3 = new User();
        user3.setId(IdGenerator.getUniqueUserId(userService));
        user3.setFirstName("Nadia");
        user3.setLastName("Najjar");
        user3.setLocation("Charlotte, NC");

        UserLogin userLogin3 = new UserLogin(user3);
        userLogin3.setEmail("nanajjar@uncc.edu");
        userLogin3.setUsername("nanajjar");
        userLogin3.setPassword("pass");
        user3.setUserLogin(userLogin3);

        // Save the users and their logins in the database
        userService.saveUser(user1);
        loginService.saveUserLogin(user1.getUserLogin());
        userService.saveUser(user2);
        loginService.saveUserLogin(user2.getUserLogin());
        userService.saveUser(user3);
        loginService.saveUserLogin(user3.getUserLogin());


        // Create some items
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Item item1 = new Item("LE Red Astro A50 Wireless Gaming Headset", "Lightly used", "Gaming, Technology, Audio", "/www.mello.com/images/headset.jpg", LocalDateTime.parse("2019-05-11 11:33:17", formatter), user1);
        Item item2 = new Item("Benchmade 15085", "New", "Knives, Hunting, Tools", "/www.mello.com/images/benchmade15085-2.jpg", LocalDateTime.parse("2019-05-04 14:19:17", formatter), user1);
        Item item3 = new Item("Computer Science Textbook", "Like new", "Programming, Education, Learning", "https://images.ezvid.com/image/upload/fl_immutable_cache/e_trim/c_pad,f_auto,h_270,q_auto:eco/l2eoa6shrebaodunfe5t", LocalDateTime.parse("2019-05-13 03:28:17", formatter), user1);
        Item item4 = new Item("Magical Instruments - MI Guitar", "Like new", "Technology, Music, Instruments", "/www.mello.com/images/mi-guitar.jpg", LocalDateTime.parse("2019-04-30 18:47:17", formatter), user2);
        Item item5 = new Item("Scuf Gaming XBox One Controller", "Moderately used", "Technology, Gaming", "/www.mello.com/images/scuf_controller.png", LocalDateTime.parse("2019-05-03 09:28:17", formatter), user2);
        Item item6 = new Item("Texas Instruments TI-84 Plus CE", "New", "Education, Electronics, Tools", "https://www.ticalculators.com/wp-content/uploads/sites/2/2015/12/TI-84_Plus_CE_graphing_calculator_by_Texas_Instruments_in_color_thinner_lighter_longer_battery_life_in_stock.jpg", LocalDateTime.parse("2019-05-12 13:12:34", formatter), user2);

        // Give user 1 the first two items
        user1.addOwnedItem(item1);
        user1.addOwnedItem(item2);
        user1.addOwnedItem(item3);

        // Give user 2 the second two items
        user2.addOwnedItem(item4);
        user2.addOwnedItem(item5);
        user2.addOwnedItem(item6);

        // Save the items to the database
        itemService.saveItem(item1);
        itemService.saveItem(item2);
        itemService.saveItem(item3);
        itemService.saveItem(item4);
        itemService.saveItem(item5);
        itemService.saveItem(item6);

        // Create some offers
        Offer offer1 = new Offer(user1, user2, item1.getId(), item5.getId());
        Offer offer2 = new Offer(user2, user1, item4.getId(), item1.getId());
        offerService.saveOffer(offer1);
        offerService.saveOffer(offer2);

        // Invalidate the session and prompt a login
        HttpSession session = request.getSession();
        session.invalidate();

        return "redirect:/login";
    }

    @GetMapping("/resetOffers")
    public String resetOffers(HttpSession session) {

        User user = (User) session.getAttribute("user");

        for (Offer o : user.getHistory()) {
            o.setAccepted(false);
            o.setRejected(false);
            o.setWithdrawn(false);
            o.setOfferClosed(false);
            o.setOfferClosedDate(null);
            if (!user.sentOffer(o.getId())) o.setMoment_viewed(null);

            if (o.getId() > 2) offerService.deleteOffer(o);
            else offerService.saveOffer(o);
        }

        resetItems(session);

        return "redirect:/offers";
    }

    @GetMapping("/resetItems")
    public String resetItems(HttpSession session) {
        for (Item i : itemService.getAllItems()) {
            i.setAvailable(true);
            i.setName(i.getName());

            itemService.saveItem(i);
        }

        User user = (User) session.getAttribute("user");
        user.setOwnedItems(itemService.getAvailableItemsByUserId(user.getId()));
        session.setAttribute("user", user);

        return "redirect:/profile";
    }


}
