package com.mello.mello.Repositories;

import com.mello.mello.Model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<UserLogin, String> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmailAndAndPassword(String email, String password);
    boolean existsByUsernameAndPassword(String email, String password);

    UserLogin findUserLoginByUser_id(String user_id);
    UserLogin findUserLoginByEmail(String email);
    UserLogin findUserLoginByUsername(String username);

}
