package com.library.book.controllers;

import com.library.book.models.Book;
import com.library.book.models.User;
import com.library.book.services.BookService;
import com.library.book.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book, @RequestParam String username) {
        User user = userService.getUserByUsername(username);
        book.setUser(user);
        return new ResponseEntity<>(bookService.saveBook(book), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/searchByTitle")
    public ResponseEntity<List<Book>> searchBookByTitle(@RequestParam String title) {
        return new ResponseEntity<>(bookService.searchBooksByTitle(title), HttpStatus.OK);
    }

    @GetMapping("/searchByAuthor")
    public ResponseEntity<List<Book>> searchBookByAuthor(@RequestParam String author) {
        return new ResponseEntity<>(bookService.searchBooksByAuthor(author), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> updateBook (@PathVariable Long id, @RequestBody Book book, @RequestParam String username) {
        User user = userService.getUserByUsername(username);
        Book existingBook = bookService.getBookById(id);

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
    public ResponseEntity<List<Book>> getAllBooksByUsername(@RequestParam String username) {
        User user = userService.getUserByUsername(username);
        return new ResponseEntity<>(bookService.getAllBooksByUser(user), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooksByUsernameAndTitle(@RequestParam String username, @RequestParam String title) {
        User user = userService.getUserByUsername(username);
        return new ResponseEntity<>(bookService.searchBooksByUserAndTitle(user, title), HttpStatus.OK);
    }

}
