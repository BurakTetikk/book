package com.library.book.services;

import com.library.book.repositories.UserRepository;
import com.library.book.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public User saveUser(User user) {
        return userRepository.save(user);
    }


    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> searchByUsername(String username) {
        return userRepository.findByUsernameContaining(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



}
