package com.mello.mello.Model;

import com.mello.mello.Services.Implementation.ItemServiceImpl;
import com.mello.mello.Services.Implementation.OfferServiceImpl;
import com.mello.mello.Services.Implementation.UserServiceImpl;
import com.mello.mello.Util.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@Table(name="users")
public class User implements Serializable {

    private static final long serialVersionUID = 1636698231313821331L;


//    @Transient private int numTrades;
//    @Transient private int numReviews;
//    @Transient private double userRating;
//    List<Offer> history of sent, received, withdrawn, accepted, declined.


    @Id
    private @NotNull String id;
    private @NotNull String firstName, lastName;
    private @NotNull String location;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL) private UserLogin userLogin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.ALL) private List<Item> ownedItems;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender", cascade = CascadeType.ALL) private List<Offer> sent;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "receiver", cascade = CascadeType.ALL) private List<Offer> received;

    @Column(columnDefinition = "VARCHAR(1000) DEFAULT \"/www.mello.com/images/default-user.png\"")
    private String imageUrl;
    private String username;

    public User() {
        this.ownedItems = new ArrayList<>();
        this.sent = new ArrayList<>();
        this.received = new ArrayList<>();
    }

    public User(String username, String email, String password) throws NoSuchAlgorithmException {
        this.userLogin = new UserLogin(this, email, username, password);
        this.id = IdGenerator.getUniqueUserId(UserServiceImpl.getInstance());
        this.firstName = "";
        this.lastName = "";
        this.location = "";
        this.ownedItems = new ArrayList<>();
        this.sent = new ArrayList<>();
        this.received = new ArrayList<>();

        this.username = username;
    }

    public User(String firstName, String lastName, String location, String username, String email, String password) throws NoSuchAlgorithmException {
        this.userLogin = new UserLogin(this, email, username, password);
        this.id = IdGenerator.getUniqueUserId(UserServiceImpl.getInstance());
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.ownedItems = new ArrayList<>();
        this.sent = new ArrayList<>();
        this.received = new ArrayList<>();

        this.username = username;
    }





    public void addOwnedItem(Item item) {
        // Loop through owned items
        for (Item i : ownedItems) {
            // If the url_value matches another owned item
            if (i.getUrlValue().equals(item.getUrlValue())) {
                // Add a number to the end of the url_value
                int counter = ItemServiceImpl.getInstance().countItemsByUrlValueContaining(item.getUrlValue());
                item.setUrlValue(item.getUrlValue() + counter);
                break;
            }
        }
        this.ownedItems.add(item);
    }


    public Item getOwnedItemById(int item_id){
        for (Item i : ownedItems)
            if (i.getId() == item_id)
                return i;
        return null;
    }

    public boolean ownsItem(Item item) {
        return getOwnedItemById(item.getId()) != null;
    }

    public boolean doesNotOwnItem(Item item) {
        return getOwnedItemById(item.getId()) == null;
    }

    public List<Item> getAvailableItems() {
        return ItemServiceImpl.getInstance().getAvailableItemsByUserId(this.id);
    }

    public List<Item> getUnavailableItems() {
        return ItemServiceImpl.getInstance().getUnavailableItemsByUserId(this.id);
    }

    public Offer getOfferById(int offer_id) {
        if (getSentOfferById(offer_id) != null)
            return getSentOfferById(offer_id);
        if (getReceivedOfferById(offer_id) != null)
            return getReceivedOfferById(offer_id);
        return null;
    }

    public Offer getSentOfferById(int offer_id){
        for (Offer o : sent)
            if (o.getId() == offer_id)
                return o;
        return null;
    }

    public Offer getReceivedOfferById(int offer_id){
        for (Offer o : received)
            if (o.getId() == offer_id)
                return o;
        return null;
    }

    // Determines whether or not the user sent the offer for differentiating history details
    public boolean sentOffer(int offer_id) {
        return OfferServiceImpl.getInstance().userWasSenderOfOffer(this.getId(), offer_id);
    }

    public boolean receivedOffer(int offer_id) {
        return OfferServiceImpl.getInstance().userWasReceiverOfOffer(this.getId(), offer_id);
    }

    public boolean doesNotHaveOfferWith(int sent_item_id, int rec_item_id) {
        return OfferServiceImpl.getInstance().offerDoesNotAlreadyExist(sent_item_id, rec_item_id);
    }

    public List<Offer> getSent() {
        return OfferServiceImpl.getInstance().getCurrentSentOffersByUserId(this.id);
    }

    public List<Offer> getHistory() {
        return OfferServiceImpl.getInstance().getHistoryByUserId(this.id);
    }


    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
        this.username = userLogin.getUsername();
    }


    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }

}
