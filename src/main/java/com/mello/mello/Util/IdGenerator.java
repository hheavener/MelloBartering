package com.mello.mello.Util;

import com.mello.mello.Services.Service.ItemService;
import com.mello.mello.Services.Service.UserService;

public class IdGenerator {

    public static String getUniqueUserId(UserService userService){
        //Generate random UserID and check it against other users in DB
        int random = (int) (Math.random() * 1000000000);
        while (userService.findUserById(Integer.toString(random)) != null)
            random = (int) (Math.random() * 1000000000);

        String id = String.format("%09d", random);
        return id;
    }

    public static int getUniqueItemId(ItemService itemService){
        //Generate random UserID and check it against other users in DB
        int random = (int) (Math.random() * 1000000000);
        while (itemService.getItemById(random) != null)
            random = (int) (Math.random() * 1000000000);

        String id_string = String.format("%09d", random);
        int id = Integer.parseInt(id_string);
        return id;
    }
}
