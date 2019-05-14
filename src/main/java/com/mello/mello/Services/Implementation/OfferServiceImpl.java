package com.mello.mello.Services.Implementation;

import com.mello.mello.Model.Offer;
import com.mello.mello.Repositories.OfferRepository;
import com.mello.mello.Services.Service.OfferService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService, InitializingBean {

    //========== Repository reference / constructor ==========//
    private final OfferRepository offerRepository;

    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }



    //========== For accessing UserService statically ==========//
    private static OfferService instance;

    @Override
    public void afterPropertiesSet() throws Exception { instance = this; }

    public static OfferService getInstance() { return instance; }


    @Override
    public void saveOffer(Offer offer) {
        offerRepository.save(offer);
    }

    @Override
    public boolean currentOfferBelongsToUser(String user_id, int offer_id) {
        return offerRepository.existsBySenderIdAndIdAndOfferClosedIsFalseOrReceiverIdAndIdAndOfferClosedIsFalse(user_id, offer_id, user_id, offer_id);
    }

    @Override
    public boolean closedOfferBelongsToUser(String user_id, int offer_id) {
        return offerRepository.existsBySenderIdAndIdAndOfferClosedIsTrueOrReceiverIdAndIdAndOfferClosedIsTrue(user_id, offer_id, user_id, offer_id);
    }

    @Override
    public boolean currentOfferBelongsToSender(String user_id, int offer_id) {
        return offerRepository.existsBySenderIdAndIdAndOfferClosedIsFalse(user_id, offer_id);
    }

    @Override
    public boolean currentOfferBelongsToReceiver(String user_id, int offer_id) {
        return offerRepository.existsByReceiverIdAndIdAndOfferClosedIsFalse(user_id, offer_id);
    }

    @Override
    public boolean offerDoesNotAlreadyExist(int sender_item_id, int receiver_item_id) {
        return !offerRepository.existsBySenderItemIdAndReceiverItemIdOrReceiverItemIdAndSenderItemId(sender_item_id, receiver_item_id, sender_item_id, receiver_item_id);
    }

    @Override
    public boolean userWasSenderOfOffer(String user_id, int offer_id) {
        return offerRepository.existsBySenderIdAndId(user_id, offer_id);
    }

    @Override
    public boolean userWasReceiverOfOffer(String user_id, int offer_id) {
        return offerRepository.existsByReceiverIdAndId(user_id, offer_id);
    }

    @Override
    public boolean actionCanBeUndone(int senderItemId, int receiverItemId) {
        // Make sure that neither item exists in another offer where accepted is true
        return !offerRepository.existsBySenderItemIdAndAcceptedIsTrueOrReceiverItemIdAndAcceptedIsTrue(senderItemId, senderItemId)
        && !offerRepository.existsBySenderItemIdAndAcceptedIsTrueOrReceiverItemIdAndAcceptedIsTrue(receiverItemId, receiverItemId);
    }

    @Override
    public Offer getOfferById(int id) {
        return offerRepository.getOfferById(id);
    }

    @Override
    public void updateMomentViewed(LocalDateTime date, int id) {
        offerRepository.updateMomentViewed(date, id);
    }

    @Override
    public List<Offer> getCurrentSentOffersByUserId(String user_id) {
//        return offerRepository.findOffersBySenderIdAndAcceptedIsFalseAndRejectedIsFalseAndWithdrawnIsFalse(user_id);
        return offerRepository.findOffersBySenderIdAndOfferClosedIsFalseOrderByOfferDateDesc(user_id);
    }

    @Override
    public List<Offer> getCurrentReceivedOffersByUserId(String user_id) {
//        return offerRepository.findOffersByReceiverIdAndAcceptedIsFalseAndRejectedIsFalseAndWithdrawnIsFalse(user_id);
        return offerRepository.findOffersByReceiverIdAndOfferClosedIsFalseOrderByOfferDateDesc(user_id);
    }

    @Override
    public List<Offer> getHistoryByUserId(String user_id) {
        return offerRepository.findOffersBySenderIdAndOfferClosedIsTrueOrReceiverIdAndOfferClosedIsTrueOrderByOfferClosedDateDesc(user_id, user_id);
    }




    @Override
    public List<Offer> getAcceptedByUserId(String user_id) {
        return offerRepository.findOffersBySenderIdAndAcceptedIsTrueOrReceiverIdAndAcceptedIsTrueOrderByOfferClosedDateDesc(user_id, user_id);
    }

    @Override
    public List<Offer> getWithdrawnByUserId(String user_id) {
        return offerRepository.findOffersBySenderIdAndWithdrawnIsTrueOrReceiverIdAndWithdrawnIsTrueOrderByOfferClosedDateDesc(user_id, user_id);
    }

    @Override
    public List<Offer> getRejectedByUserId(String user_id) {
        return offerRepository.findOffersBySenderIdAndRejectedIsTrueOrReceiverIdAndRejectedIsTrueOrderByOfferClosedDateDesc(user_id, user_id);
    }




    public void closeAllInstancesOfItemAcrossMultipleOffers(String user_id, int item_id) {

        // Close sent offers containing item
        for (Offer o : offerRepository.findOffersBySenderItemIdAndOfferClosedIsFalse(item_id)) {
            o.setWithdrawn(true);
            o.setOfferClosed(true);
            o.setOfferClosedDate(LocalDateTime.now());
            saveOffer(o);
        }
        // Close received offers containing item
        for (Offer o : offerRepository.findOffersByReceiverItemIdAndOfferClosedIsFalse(item_id)) {
            o.setRejected(true);
            o.setOfferClosed(true);
            o.setOfferClosedDate(LocalDateTime.now());
            saveOffer(o);
        }

    }

    @Override
    public void closeOffer(Offer offer, String action) {

        switch (action) {
            case Offer.ACCEPTED:
                // Get the users ids
                String accepter_id = offer.getReceiver().getId();
                String otherUser_id = offer.getSender().getId();

                // Get the item ids
                int accepterItemId = offer.getReceiverItemId();
                int otherUserItemId = offer.getSenderItemId();

                // Close the offer
                offer.setAccepted(true);
                offer.setOfferClosed(true);
                offer.setOfferClosedDate(LocalDateTime.now());
                saveOffer(offer);

                // Set the items to unavailable
                ItemServiceImpl.getInstance().setOfferItemsToUnavailable(accepterItemId, otherUserItemId);

                // Close other offers containing those items
                closeOffersContainingUserAndItem(accepter_id, offer.getReceiverItemId());
                closeOffersContainingUserAndItem(otherUser_id, offer.getSenderItemId());

                break;

            case Offer.REJECTED:
                break;

            case Offer.WITHDRAWN:
                break;
        }

    }

    @Override
    public void closeOffersContainingUserAndItem(String user_id, int item_id) {
        List<Offer> offers = offerRepository.findOffersBySenderIdAndSenderItemIdAndOfferClosedIsFalse(user_id, item_id);
        offers.addAll(offerRepository.findOffersByReceiverIdAndReceiverItemIdAndOfferClosedIsFalse(user_id, item_id));

        for (Offer offer : offers) {
            if (offer.getSenderItemId() == item_id) offer.setWithdrawn(true);
            if (offer.getReceiverItemId() == item_id) offer.setRejected(true);
            offer.setOfferClosed(true);
            offer.setOfferClosedDate(LocalDateTime.now());
            saveOffer(offer);
        }

    }



    public void deleteOffer(Offer offer) {
        offerRepository.delete(offer);
    }

    @Override
    public void deleteAllOffersContainingItem(int item_id) {
        offerRepository.removeAllBySenderItemIdOrReceiverItemId(item_id, item_id);
    }


    @Override
    public void deleteAllEntries() {
        offerRepository.deleteAll();
    }
}
