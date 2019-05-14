package com.mello.mello.Services.Implementation;

import com.mello.mello.Model.UserLogin;
import com.mello.mello.Repositories.LoginRepository;
import com.mello.mello.Services.Service.LoginService;
import com.mello.mello.Util.Hash;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class LoginServiceImpl implements LoginService, InitializingBean {

    //========== Repository reference / constructor ==========//
    private final LoginRepository loginRepository;

    public LoginServiceImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }




    //========== For accessing UserService statically ==========//
    private static LoginService instance;

    @Override
    public void afterPropertiesSet() throws Exception { instance = this; }

    public static LoginService getInstance() { return instance; }


    @Override
    public boolean emailAlreadyInUse(String email) {
        return loginRepository.existsByEmail(email);
    }

    @Override
    public boolean usernameAlreadyInUse(String username) {
        return loginRepository.existsByUsername(username);
    }

    @Override
    public boolean validateUserByEmail(String email, String password) throws NoSuchAlgorithmException {
        if (loginRepository.existsByEmail(email)) {
            String salt = loginRepository.findUserLoginByEmail(email).getSalt();
            return loginRepository.existsByEmailAndAndPassword(email, Hash.hashAndSalt(password, salt));
        }
        return false;
    }

    @Override
    public boolean validateUserByUsername(String username, String password) throws NoSuchAlgorithmException {
//        return loginRepository.existsByUsernameAndPassword(username, password);
        if (loginRepository.existsByUsername(username)) {
            String salt = loginRepository.findUserLoginByUsername(username).getSalt();
            return loginRepository.existsByUsernameAndPassword(username, Hash.hashAndSalt(password, salt));
        }
        return false;
    }

    @Override
    public void saveUserLogin(UserLogin userLogin) {
        loginRepository.save(userLogin);
    }


    @Override
    public UserLogin findUserLoginByUser_id(String user_id) {
        return loginRepository.findUserLoginByUser_id(user_id);
    }

    @Override
    public UserLogin findUserLoginByEmail(String email) {
        return loginRepository.findUserLoginByEmail(email);
    }

    @Override
    public UserLogin findUserLoginByUsername(String username) {
        return loginRepository.findUserLoginByUsername(username);
    }


    @Override
    public void deleteAllEntries() {
        loginRepository.deleteAll();
    }
}
