package com.mello.mello.Model;

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
@Table(name="items")
public class Item implements Serializable {

    private static final long serialVersionUID = 3104792797115398596L;

    @Id @GeneratedValue
    private @NotNull int id;
    private @NotNull String name, urlValue, item_condition, categories, image_url;
    private @NotNull LocalDateTime postDate;
    private @NotNull boolean available, deletedByUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_Item_UserID"))
    private @NotNull User owner;


    public Item() {
        this.owner = new User();
        this.available = true;
        this.deletedByUser = false;
    }

    public Item(String name, String item_condition, String categories, String image_url, LocalDateTime postDate, User owner) {
        this.name = name;
        this.urlValue = createUrlValue(name);
        this.item_condition = item_condition;
        this.categories = categories;
        this.image_url = image_url;
        this.postDate = postDate;
        this.owner = owner;
        this.available = true;
        this.deletedByUser = false;
    }


    public void setName(String name) {
        this.name = name;
        this.urlValue = createUrlValue(name);
    }


    private String createUrlValue(String name) {
        // Replace non-alphanumeric characters with a space, then replace whitespace with a "-"
        return name.toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s+", "-");
    }

    public String getTimeElapsed() {
        return DateHelper.getTimeElapsed(this.getPostDate());
    }


}
