package com.mello.mello.Services.Service;


import com.mello.mello.Model.Offer;
import com.mello.mello.Model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface OfferService {



    void saveOffer(Offer offer);
    void updateMomentViewed(LocalDateTime date, int id);

    // These methods are for ensuring that a user is permitted to perform an action on a current offer
    boolean currentOfferBelongsToUser(String user_id, int offer_id);
    boolean closedOfferBelongsToUser(String user_id, int offer_id);
    boolean currentOfferBelongsToSender(String user_id, int offer_id);
    boolean currentOfferBelongsToReceiver(String user_id, int offer_id);
    boolean offerDoesNotAlreadyExist(int sender_item_id, int receiver_item_id);

    // For determining if user was the sender or receiver of a given offer (for use with History items)
    boolean userWasSenderOfOffer(String user_id, int offer_id);
    boolean userWasReceiverOfOffer(String user_id, int offer_id);

    // Determines whether or not a user can undo a withdraw or decline
    boolean actionCanBeUndone(int senderItemId, int receiverItemId);

    Offer getOfferById(int id);

    List<Offer> getCurrentSentOffersByUserId(String user_id);
    List<Offer> getCurrentReceivedOffersByUserId(String user_id);

    List<Offer> getHistoryByUserId(String user_id);
    List<Offer> getAcceptedByUserId(String user_id);
    List<Offer> getWithdrawnByUserId(String user_id);
    List<Offer> getRejectedByUserId(String user_id);


    void closeOffer(Offer offer, String action);
    void closeOffersContainingUserAndItem(String user_id, int item_id);

    void deleteOffer(Offer offer);
    void deleteAllOffersContainingItem(int item_id);



    void deleteAllEntries();
}
