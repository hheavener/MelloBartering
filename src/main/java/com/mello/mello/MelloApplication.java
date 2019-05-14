package com.mello.mello;

import com.mello.mello.Model.User;
import com.mello.mello.Repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan
@EnableJpaRepositories("com.mello.mello.Repositories")
public class MelloApplication {

    private static UserRepository userRepository;

    public static void main(String[] args) {

        SpringApplication.run(MelloApplication.class, args);

    }

}
