package com.library.book.controllers;

import com.library.book.dto.UserDto;
import com.library.book.entity.UserEntity;
import com.library.book.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor  //@RequiredArgsConstructor kullandığında final olan alanları kendisi inject ediyor
public class UserController {


    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        try {
            return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //404 Bad Request
        }

    }

    @GetMapping("/all")
    public ResponseEntity<Page<UserDto>> getAllUsers(@PageableDefault(sort = "username", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(userService.getAllUsers(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/search-username")
    public ResponseEntity<Page<UserDto>> searchByUsername(@RequestParam String username, @PageableDefault(sort = "username", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(userService.searchByUsername(username, pageable), HttpStatus.OK);
    }
}
