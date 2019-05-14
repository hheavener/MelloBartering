package com.mello.mello.Services.Implementation;

import com.mello.mello.Model.User;
import com.mello.mello.Repositories.UserRepository;
import com.mello.mello.Services.Service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, InitializingBean {

    //========== Repository reference / constructor ==========//
    private final UserRepository userRepository;

    public UserServiceImpl( UserRepository userRepository) {this.userRepository = userRepository;}



    //========== For accessing UserService statically ==========//
    private static UserService instance;

    @Override
    public void afterPropertiesSet() throws Exception { instance = this; }

    public static UserService getInstance() { return instance; }


    @Override
    public User findUserById(String id) {
        User user = userRepository.findUserById(id);
        if (user != null) user.setUserLogin(LoginServiceImpl.getInstance().findUserLoginByUser_id(id));
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        User user = LoginServiceImpl.getInstance().findUserLoginByUsername(username).getUser();
        if (user != null) user.setUserLogin(LoginServiceImpl.getInstance().findUserLoginByUsername(username));
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = LoginServiceImpl.getInstance().findUserLoginByEmail(email).getUser();
        if (user != null) user.setUserLogin(LoginServiceImpl.getInstance().findUserLoginByEmail(email));
        return user;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }


    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


    @Override
    public void deleteAllEntries() {
        userRepository.deleteAll();
    }
}
