package com.mello.mello.Services.Service;

import com.mello.mello.Model.User;

public interface UserService {

    User findUserById(String id);

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    void saveUser(User user);

    boolean existsByUsername(String username);

    void deleteAllEntries();

}
