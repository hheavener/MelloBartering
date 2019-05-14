package com.mello.mello.Services.Service;

import com.mello.mello.Model.UserLogin;

import java.security.NoSuchAlgorithmException;

public interface LoginService {

    boolean emailAlreadyInUse(String email);

    boolean usernameAlreadyInUse(String username);

    boolean validateUserByEmail(String email, String password) throws NoSuchAlgorithmException;

    boolean validateUserByUsername(String username, String password) throws NoSuchAlgorithmException;

    void saveUserLogin(UserLogin userLogin);

    UserLogin findUserLoginByUser_id(String user_id);
    UserLogin findUserLoginByEmail(String email);
    UserLogin findUserLoginByUsername(String username);



    void deleteAllEntries();
}
