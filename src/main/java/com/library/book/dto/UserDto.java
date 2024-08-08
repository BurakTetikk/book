package com.library.book.dto;

import jakarta.persistence.Entity;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    private String firstName;
    private String lastName;

    @NotEmpty(message = "Password should not be empty")
    private String password;

    @Email
    @NotEmpty(message = "Email should not be empty")
    private String email;

    private Set<BookDto> books;
}
