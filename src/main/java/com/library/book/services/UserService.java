package com.library.book.services;

import com.library.book.repositories.UserRepository;
import com.library.book.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }


    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserEntity> searchByUsername(String username) {
        return userRepository.findByUsernameContaining(username);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }



}
