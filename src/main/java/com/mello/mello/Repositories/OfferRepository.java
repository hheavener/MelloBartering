package com.mello.mello.Repositories;

import com.mello.mello.Model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, String> {

    Offer getOfferById(int id);
//    Offer getOfferBySenderIdAndId

    @Modifying
    @Query(value = "UPDATE Offers SET moment_viewed = ? WHERE id = ?", nativeQuery = true)
    void updateMomentViewed(LocalDateTime date, int id);



    // Current sent offers
    List<Offer> findOffersBySenderIdAndOfferClosedIsFalseOrderByOfferDateDesc(String sender_id);
    // Current received offers
    List<Offer> findOffersByReceiverIdAndOfferClosedIsFalseOrderByOfferDateDesc(String receiver_id);

    // History by User ID
    List<Offer> findOffersBySenderIdAndOfferClosedIsTrueOrReceiverIdAndOfferClosedIsTrueOrderByOfferClosedDateDesc(String sender_id, String receiver_id);

    // All offers containing specified item belonging to user (for closing multiple offers containing the same item)
    List<Offer> findOffersBySenderIdAndSenderItemIdAndOfferClosedIsFalse(String sender_id, int item_id);
    List<Offer> findOffersByReceiverIdAndReceiverItemIdAndOfferClosedIsFalse(String receiver_id, int item_id);

    List<Offer> findOffersBySenderItemIdAndOfferClosedIsFalse(int item_id);
    List<Offer> findOffersByReceiverItemIdAndOfferClosedIsFalse(int item_id);



    // For getting different lists for displaying user's history
    // Accepted
    List<Offer> findOffersBySenderIdAndAcceptedIsTrueOrReceiverIdAndAcceptedIsTrueOrderByOfferClosedDateDesc(String sender_id, String receiver_id);
    // Rejected
    List<Offer> findOffersBySenderIdAndRejectedIsTrueOrReceiverIdAndRejectedIsTrueOrderByOfferClosedDateDesc(String sender_id, String receiver_id);
    // Withdrawn
    List<Offer> findOffersBySenderIdAndWithdrawnIsTrueOrReceiverIdAndWithdrawnIsTrueOrderByOfferClosedDateDesc(String sender_id, String receiver_id);



    // For checking if the user has any current sent or received items
    boolean existsBySenderIdAndIdAndOfferClosedIsFalse(String sender_id, int offer_id);
    boolean existsByReceiverIdAndIdAndOfferClosedIsFalse(String sender_id, int offer_id);

    // For determining whether or not a CURRENT offer belongs to a user
    boolean existsBySenderIdAndIdAndOfferClosedIsFalseOrReceiverIdAndIdAndOfferClosedIsFalse(String sender_id, int offer_id1, String receiver_id, int offer_id2);
    // For determining whether or not a CLOSED offer belongs to a user
    boolean existsBySenderIdAndIdAndOfferClosedIsTrueOrReceiverIdAndIdAndOfferClosedIsTrue(String sender_id, int offer_id1, String receiver_id, int offer_id2);

    // For determining whether or not a user was the sender or the receiver of a particular offer
    boolean existsBySenderIdAndId(String sender_id, int offer_id);
    boolean existsByReceiverIdAndId(String receiver_id, int offer_id);

    // Determines if an item has been accepted in an offer (if not, a withdrawn or declined offer can be undone)
    boolean existsBySenderItemIdAndAcceptedIsTrueOrReceiverItemIdAndAcceptedIsTrue(int senderItemId, int sendItemIdAgain);

    // Determines if an offer with the two items already exists
    boolean existsBySenderItemIdAndReceiverItemIdOrReceiverItemIdAndSenderItemId(int sendItemId, int recItemId, int sendItemIdAgain, int recItemIdAgain);

    // When someone permanently deletes an item
    @Transactional
    void removeAllBySenderItemIdOrReceiverItemId(int sendItemId, int recItemId);

}
