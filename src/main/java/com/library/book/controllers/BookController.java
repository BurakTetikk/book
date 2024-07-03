package com.library.book.controllers;

import com.library.book.dto.BookDto;
import com.library.book.entity.BookEntity;
import com.library.book.entity.UserEntity;
import com.library.book.services.BookService;
import com.library.book.services.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor //@RequiredArgsConstructor kullandığında final olan alanları kendisi inject ediyor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<BookEntity> createBook(@RequestBody BookEntity book, @RequestParam String username) {
        UserEntity user = userService.getUserByUsername(username);
        book.setUser(user);
        return new ResponseEntity<>(bookService.saveBook(book), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    //Şİmdilkik burada dto dönüş yaptım diğerlerine de uygularsın
    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/searchByTitle")
    public ResponseEntity<List<BookEntity>> searchBookByTitle(@RequestParam String title) {
        return new ResponseEntity<>(bookService.searchBooksByTitle(title), HttpStatus.OK);
    }

    @GetMapping("/searchByAuthor")
    public ResponseEntity<List<BookEntity>> searchBookByAuthor(@RequestParam String author) {
        return new ResponseEntity<>(bookService.searchBooksByAuthor(author), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<BookEntity> updateBook(@PathVariable Long id, @RequestBody BookEntity book, @RequestParam String username) {
        UserEntity user = userService.getUserByUsername(username);
        BookEntity existingBook = bookService.getBookById(id);

        if (existingBook != null && existingBook.getUser().equals(user)) {
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setISBN(book.getISBN());
            existingBook.setPrice(book.getPrice());
            existingBook.setStock(book.getStock());
            return new ResponseEntity<>(bookService.saveBook(existingBook), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<BookEntity>> getAllBooksByUsername(@RequestParam String username) {
        UserEntity user = userService.getUserByUsername(username);
        return new ResponseEntity<>(bookService.getAllBooksByUser(user), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookEntity>> searchBooksByUsernameAndTitle(@RequestParam String username, @RequestParam String title) {
        UserEntity user = userService.getUserByUsername(username);
        return new ResponseEntity<>(bookService.searchBooksByUserAndTitle(user, title), HttpStatus.OK);
    }

}
