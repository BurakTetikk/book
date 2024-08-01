package com.library.book.security;

import com.library.book.entity.UserEntity;
import com.library.book.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(){}

    public CustomUserDetailsService(UserRepository repository){
        this.userRepository = repository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserEntity userEntity = userRepository.findByUsername(username);
            return new CustomUserDetails(userEntity);
        }catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
