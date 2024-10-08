package com.library.book.controllers;

import com.library.book.dto.BookDto;
import com.library.book.dto.UserDto;
import com.library.book.entity.BookEntity;
import com.library.book.entity.UserEntity;
import com.library.book.services.BookService;
import com.library.book.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final BookService bookService;

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);

        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        UserEntity existingUser = userService.getUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {

            result.rejectValue("email", null,
                    "There is already an account registered with the same email!");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);

        return "redirect:/register?success";
    }

    @PostMapping("/add-book/save")
    public String createBook(@Valid @ModelAttribute("book") BookDto bookDto,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()) {
            model.addAttribute("book", bookDto);
            return "/add-book";
        }

        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(bookDto.getTitle());
        bookEntity.setAuthor(bookDto.getAuthor());
        bookEntity.setISBN(bookDto.getISBN());
        bookEntity.setPrice(bookDto.getPrice());
        bookEntity.setStock(bookDto.getStock());


        bookService.saveBook(bookDto);

        model.addAttribute("successMessage", "Kitap başarıyla eklendi!");

        return "redirect:/add-book?success";
    }

    @GetMapping("/add-book")
    public String addBook() {
        /*(@Valid @ModelAttribute("book") BookDto bookDto,
                          BindingResult result,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("book", bookDto);
            return "/add-book";
        }

         BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(bookDto.getTitle());
        bookEntity.setAuthor(bookDto.getAuthor());
        bookEntity.setISBN(bookDto.getISBN());
        bookEntity.setPrice(bookDto.getPrice());
        bookEntity.setStock(bookDto.getStock());


        bookService.saveBook(bookDto);

        model.addAttribute("successMessage", "Kitap başarıyla eklendi!");*/

        return "/add-book";
    }


    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("user", users);
        return "users";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/search")
    public String getBooksByTitle(Model model) {
        List<BookEntity> bookEntities = bookService.getAllBooks();
        model.addAttribute(bookEntities);
        return "search";
    }

}
