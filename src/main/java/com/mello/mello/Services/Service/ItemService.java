package com.mello.mello.Services.Service;

import com.mello.mello.Model.Item;
import com.mello.mello.Model.User;

import java.util.List;

public interface ItemService {

    String getUniqueId();

    Item getItemById(int id);
    Item getItemByOwnerUsernameAndUrlValue(User owner, String url_value);

    void saveItem(Item item);
    void removeItem(Item item);
    void setOfferItemsToUnavailable(int senderItemId, int receiverItemId);

    boolean itemBelongsToUser(int item_id, String user_id);
    boolean itemIsAvailable(int item_id);

    int countItemsByUrlValueContaining(String url_value);

    List<Item> getAllItems();
    List<Item> getAllItemsByCategory(String category);
    List<Item> getAllItemsByCategories(List<String> categories);

    List<Item> getItemsByUsername(String username);
    List<Item> getItemsByUserEmail(String email);

    List<Item> getAvailableItemsByUserId(String id);
    List<Item> getUnavailableItemsByUserId(String id);

    List<Item> getItemsByCount(int startIndex, int endIndex);



    void deleteAllEntries();
}

