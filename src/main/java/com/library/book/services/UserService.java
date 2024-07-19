package com.library.book.services;

import com.library.book.dto.UserDto;
import com.library.book.exceptions.ResourceNotFoundException;
import com.library.book.mapper.MapperUtil;
import com.library.book.repositories.UserRepository;
import com.library.book.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final MapperUtil mapperUtil;



    public UserDto saveUser(UserDto userDto) {
        UserEntity user = mapperUtil.convert(userDto, new UserEntity());
        userRepository.save(user);
        return mapperUtil.convert(user, new UserDto());
    }


    public UserDto getUserById(Long id) {
        UserEntity user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+ id));

            return mapperUtil.convert(user, new UserDto());

    }

    public UserDto getUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {
            throw new ResourceNotFoundException("User not found with username: " + username);
        }

        return mapperUtil.convert(user, new UserDto());
    }

    public Page<UserDto> searchByUsername(String username, Pageable pageable) {
        Page<UserEntity> users = userRepository.findByUsernameContaining(username, pageable);

        if (users.isEmpty()){
            throw new ResourceNotFoundException("User not found with username: " + username);
        }
        return users.map(userEntity -> mapperUtil.convert(userEntity, new UserDto()));
    }

    public Page<UserDto> getAllUsers(Pageable pageable) {
        Page<UserEntity> users = userRepository.findAll(pageable);
        return users
                .map(userEntity -> mapperUtil.convert(userEntity, new UserDto()));
    }
}
