package com.mello.mello.Services.Implementation;

import com.mello.mello.Model.Item;
import com.mello.mello.Model.User;
import com.mello.mello.Repositories.ItemRepository;
import com.mello.mello.Services.Service.ItemService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService, InitializingBean {

    //========== Repository reference / constructor ==========//
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }




    //========== For accessing UserService statically ==========//
    private static ItemService instance;

    @Override
    public void afterPropertiesSet() throws Exception { instance = this; }

    public static ItemService getInstance() { return instance; }




    //========== Service methods ==========//
    @Override
    public String getUniqueId() {
//        int random = (int) (Math.random() * 1000000000);
//        while (getItemById(Integer.toString(random)) != null)
//            random = (int) (Math.random() * 1000000000);
//
//        String id = String.format("%09d", random);
//        return id;
        return "";
    }

    @Override
    public Item getItemById(int id) {
        return itemRepository.findItemById(id);
    }

    @Override
    public Item getItemByOwnerUsernameAndUrlValue(User owner, String url_value) {
        return itemRepository.findItemByOwnerAndUrlValue(owner, url_value);
    }

    @Override
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public void removeItem(Item item) {
        itemRepository.delete(item);
    }

    @Override
    public void setOfferItemsToUnavailable(int senderItemId, int receiverItemId) {
        Item sent = itemRepository.findItemById(senderItemId);
        Item received = itemRepository.findItemById(receiverItemId);
        sent.setAvailable(false);
        received.setAvailable(false);
        saveItem(sent);
        saveItem(received);
    }

    @Override
    public boolean itemBelongsToUser(int item_id, String user_id) {
        return itemRepository.existsByIdAndOwnerId(item_id, user_id);
    }

    @Override
    public boolean itemIsAvailable(int item_id) {
        return itemRepository.existsByIdAndAvailableIsTrue(item_id);
    }

    @Override
    public int countItemsByUrlValueContaining(String url_value) {
        return itemRepository.countItemsByUrlValueContaining(url_value);
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> getAllItemsByCategory(String category) {
        return itemRepository.findAllByCategoriesContaining(category);
    }

    @Override
    public List<Item> getAllItemsByCategories(List<String> categories) {
        List<Item> items = new ArrayList<>();
        for (String s : categories) items.addAll(getAllItemsByCategory(s));
        return items;
    }


    @Override
    public List<Item> getItemsByUsername(String username) {
        String userId = UserServiceImpl.getInstance().findUserByUsername(username).getId();
        return getAvailableItemsByUserId(userId);
    }

    @Override
    public List<Item> getItemsByUserEmail(String email) {
        String userId = UserServiceImpl.getInstance().findUserByEmail(email).getId();
        return getAvailableItemsByUserId(userId);
    }

    @Override
    public List<Item> getAvailableItemsByUserId(String id) {
        return itemRepository.findAllByOwnerIdAndAvailableIsTrueOrderByPostDateDesc(id);
    }

    @Override
    public List<Item> getUnavailableItemsByUserId(String id) {
        return itemRepository.findAllByOwnerIdAndAvailableIsFalseOrderByPostDateDesc(id);
    }

    @Override
    public List<Item> getItemsByCount(int startIndex, int endIndex) {
        return null;
    }


    @Override
    public void deleteAllEntries() {
        itemRepository.deleteAll();
    }
}
