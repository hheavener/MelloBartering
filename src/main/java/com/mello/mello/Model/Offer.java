package com.mello.mello.Model;

import com.mello.mello.Services.Implementation.ItemServiceImpl;
import com.mello.mello.Services.Implementation.OfferServiceImpl;
import com.mello.mello.Util.DateHelper;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@Table(name="offers")
public class Offer implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    public static final String ACCEPTED = "accepted";
    public static final String REJECTED = "rejected";
    public static final String WITHDRAWN = "withdrawn";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private @NotNull int id;
    private LocalDateTime offerDate, moment_viewed, offerClosedDate;
    private boolean accepted, rejected, withdrawn, offerClosed;

    @ManyToOne
    @JoinColumn(name = "sender_id", foreignKey = @ForeignKey(name = "FK_Offer_Sender_Id"))
    private @NotNull User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", foreignKey = @ForeignKey(name = "FK_Offer_Receiver_Id"))
    private @NotNull User receiver;

    @Column(name = "sender_item_id" /*, columnDefinition = "VARCHAR(1000)"*/)
    private @NotNull int senderItemId;

    @Column(name = "receiver_item_id" /*, columnDefinition = "VARCHAR(1000)"*/)
    private @NotNull int receiverItemId;


    public Offer() {
        this.sender = new User();
        this.receiver = new User();

        this.offerDate = LocalDateTime.now();
        this.moment_viewed = null;
        this.offerClosedDate = null;
        this.accepted = false;
        this.rejected = false;
        this.withdrawn = false;
        this.offerClosed = false;
    }

    /**
     * Offer for one-on-one trades.
     * @param sender User sending the offer
     * @param receiver User receiving the offer
     * @param senderItemId id of the Item belonging to the sender
     * @param receiverItemId id of the Item belonging to the receiver
     */
    public Offer(User sender, User receiver, int senderItemId, int receiverItemId) {
        this.sender = sender;
        this.receiver = receiver;
        this.senderItemId = senderItemId;
        this.receiverItemId = receiverItemId;

        this.offerDate = LocalDateTime.now();
        this.moment_viewed = null;
        this.offerClosedDate = null;
        this.accepted = false;
        this.rejected = false;
        this.withdrawn = false;
        this.offerClosed = false;
    }


    public Item getSenderItem(){
        return ItemServiceImpl.getInstance().getItemById(this.senderItemId);
    }

    public Item getReceiverItem(){
        return ItemServiceImpl.getInstance().getItemById(this.receiverItemId);
    }

    public String getTimeElapsed(LocalDateTime date) {
        return DateHelper.getTimeElapsed(date);
    }

    public String getFormattedDate(LocalDateTime date) {
        return DateHelper.formatLocalDateTime(date);
    }

    public boolean actionCanBeUndone() {
        return OfferServiceImpl.getInstance().actionCanBeUndone(this.senderItemId, this.receiverItemId)
                && ItemServiceImpl.getInstance().itemIsAvailable(this.senderItemId)
                && ItemServiceImpl.getInstance().itemIsAvailable(this.receiverItemId);
    }

    public String getAction() {
        if (accepted) return Offer.ACCEPTED;
        else if (rejected) return Offer.REJECTED;
        else return Offer.WITHDRAWN;
    }
}

















// ================= Embedded Composite Key ================= //
//@Data
//@Embeddable
//public static class PrimaryKey implements Serializable {
//
//    @ManyToOne
//    @JoinColumn(name = "sender_id", foreignKey = @ForeignKey(name = "FK_Offer_Sender_Id"))
//    private @NotNull User sender;
//
//    @ManyToOne
//    @JoinColumn(name = "receiver_id", foreignKey = @ForeignKey(name = "FK_Offer_Receiver_Id"))
//    private @NotNull User receiver;
//
//    @Column(name = "sender_item_ids", columnDefinition = "VARCHAR(1000)")
//    private @NotNull String senderItemId;
//
//    @Column(name = "receiver_item_ids", columnDefinition = "VARCHAR(1000)")
//    private @NotNull String receiverItemId;
//
//}


// ================= Future Offer Methods ================= //

//    /**
//     * Offer for many-on-many trades.
//     * @param sender User sending the offer
//     * @param receiver User receiving the offer
//     * @param senderItemId ArrayList of Integer Ids of the items the sender is offering
//     * @param receiverItemId ArrayList of Integer Ids of the items the receiver owns
//     */
//    public Offer(User sender, User receiver, ArrayList<Integer> senderItemId, ArrayList<Integer> receiverItemId) {
//        this.sender = sender;
//        this.receiver = receiver;
//        this.senderItemId = senderItemId.toString();
//        this.receiverItemId = receiverItemId.toString();
//
//        this.offerDate = LocalDateTime.now();
//        this.moment_viewed = null;
//        this.accepted = false;
//        this.rejected = false;
//        this.withdrawn = false;
//        this.offerClosed = false;
//    }
//
//    /**
//     * Offer for one-to-many trades (one sent in exchange for many).
//     * @param sender User sending the offer
//     * @param receiver User receiving the offer
//     * @param senderItemId Integer Id of the item the sender is offering
//     * @param receiverItemId ArrayList of Integer Ids of the items the receiver owns
//     */
//    public Offer(User sender, User receiver, int senderItemId, ArrayList<Integer> receiverItemId) {
//        this.sender = sender;
//        this.receiver = receiver;
//        this.senderItemId = Integer.toString(senderItemId);
//        this.receiverItemId = receiverItemId.toString();
//
//        this.offerDate = LocalDateTime.now();
//        this.moment_viewed = null;
//        this.accepted = false;
//        this.rejected = false;
//        this.withdrawn = false;
//        this.offerClosed = false;
//    }
//
//    /**
//     * Offer for many-to-one trades (many sent in exchange for one).
//     * @param sender User sending the offer
//     * @param receiver User receiving the offer
//     * @param senderItemId ArrayList of Integer Ids of the items the sender is offering
//     * @param receiverItemId Integer id of the item the receiver owns
//     */
//    public Offer(User sender, User receiver, ArrayList<Integer> senderItemId, int receiverItemId) {
//        this.sender = sender;
//        this.receiver = receiver;
//        this.senderItemId = senderItemId.toString();
//        this.receiverItemId = Integer.toString(receiverItemId);
//
//        this.offerDate = LocalDateTime.now();
//        this.moment_viewed = null;
//        this.accepted = false;
//        this.rejected = false;
//        this.withdrawn = false;
//        this.offerClosed = false;
//    }