package org.example.service;

import org.example.model.AuthUser;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBServiceS {
    @Autowired
    public DBServiceS(UserRepository userRepository) {
//        AuthUser authUser = new AuthUser();
//        authUser.setId(1124);
//        authUser.setUsername("abboss");
//        authUser.setPassword("123");
//        authUser.setRole("USER");
//        userRepository.save(authUser);
//        authUser = new AuthUser();
//        authUser.setId(132);
//        authUser.setUsername("Islamm");
//        authUser.setPassword("12345");
//        authUser.setRole("ADMIN");
//        userRepository.save(authUser);
    }
}
