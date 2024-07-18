package com.library.book.controllers;

import com.library.book.dto.UserDto;
import com.library.book.entity.UserEntity;
import com.library.book.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/searchByUsername")
    public ResponseEntity<List<UserDto>> searchByUsername(@RequestParam String username) {
        return new ResponseEntity<>(userService.searchByUsername(username), HttpStatus.OK);
    }
}
