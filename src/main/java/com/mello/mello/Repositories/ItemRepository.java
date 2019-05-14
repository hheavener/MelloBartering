package com.mello.mello.Repositories;

import com.mello.mello.Model.Item;
import com.mello.mello.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

    List<Item> findAllByCategoriesContaining(String category);
    List<Item> findAllByOwnerIdAndAvailableIsTrueOrderByPostDateDesc(String owner_id);
    List<Item> findAllByOwnerIdAndAvailableIsFalseOrderByPostDateDesc(String owner_id);

    Item findItemById(int id);
    Item findItemByOwnerIdAndUrlValue(String owner_id, String url_value);
    Item findItemByOwnerAndUrlValue(User owner, String url_value);

    int countItemsByUrlValueContaining(String url_value);

    boolean existsByOwner(String owner_id);
    boolean existsByOwnerId(String owner_id);
    boolean existsByOwner_Id(String owner_id);
    boolean existsByIdAndOwnerId(int item_id, String user_id);

    // Determines whether or not the item is still available
    boolean existsByIdAndAvailableIsTrue(int item_id);

}
