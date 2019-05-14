package com.mello.mello.Repositories;

import com.mello.mello.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findUserById(String id);
//    User findUserByEmail(String email);
//    User findUserByUsername(String username);

    boolean existsByUsername(String username);

}
