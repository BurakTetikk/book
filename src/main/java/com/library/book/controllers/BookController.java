package com.library.book.controllers;

import com.library.book.dto.BookDto;
import com.library.book.dto.UserDto;
import com.library.book.entity.BookEntity;
import com.library.book.entity.UserEntity;
import com.library.book.exceptions.BadRequestException;
import com.library.book.mapper.MapperUtil;
import com.library.book.services.BookService;
import com.library.book.services.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    private final MapperUtil mapperUtil;

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto, @RequestParam String username) {
        UserDto userDto = userService.getUserByUsername(username);

        bookDto.setUser(mapperUtil.convert(userDto, new UserEntity()));
        return new ResponseEntity<>(bookService.saveBook(bookDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    //Şİmdilkik burada dto dönüş yaptım diğerlerine de uygularsın
    @GetMapping("/all")
    public ResponseEntity<Page<BookDto>> getAllBooks(@PageableDefault(sort = "author", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(bookService.getAllBooks(pageable), HttpStatus.OK);
    }

    @GetMapping("/search-title")
    public ResponseEntity<Page<BookDto>> searchBookByTitle(@RequestParam String title,
                                                           @PageableDefault(sort = {"title"}) Pageable pageable) {

       // Pageable pageable = PageRequest.of(page - 1, size);

        return new ResponseEntity<>(bookService.searchBooksByTitle(title, pageable), HttpStatus.OK);
    }

    @GetMapping("/search-author")
    public ResponseEntity<Page<BookDto>> searchBookByAuthor(@RequestParam String author, @PageableDefault(sort = "title", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(bookService.searchBooksByAuthor(author, pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto book, @RequestParam String username) {
        UserDto user = userService.getUserByUsername(username);
        BookDto existingBook = bookService.getBookById(id);

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
    public ResponseEntity<Page<BookDto>> getAllBooksByUser(@RequestParam String username,
                                                               @PageableDefault(sort = "title", direction = Sort.Direction.DESC) Pageable pageable) {
        UserDto user = userService.getUserByUsername(username);
        return new ResponseEntity<>(bookService.getAllBooksByUser(user, pageable), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookDto>> searchBooksByUsernameAndTitle(@RequestParam String username,
                                                                       @RequestParam String title,
                                                                       @PageableDefault Pageable pageable) {
        UserDto user = userService.getUserByUsername(username);
        return new ResponseEntity<>(bookService.searchBooksByUserAndTitle(user, title, pageable), HttpStatus.OK);
    }

    @GetMapping("/searchPage")
    public ResponseEntity<Page<BookDto>> searchBooksByTitle(@RequestParam String title,
                                                            @RequestParam int page,
                                                            @RequestParam int size) {
        return new ResponseEntity<>(bookService.searchBooksByTitle(title, page, size), HttpStatus.OK);
    }

    @GetMapping("/search-page")
    public ResponseEntity<Page<BookDto>> getAllBooksPage(@RequestParam int page,
                                                         @RequestParam int size) {
        return new ResponseEntity<>(bookService.getAllPage(page, size), HttpStatus.OK);
    }
    @GetMapping("/price-less-than")
    public ResponseEntity<Page<BookDto>> getBooksPriceLessThan(@RequestParam String price,
                                                               @RequestParam int page,
                                                               @RequestParam int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("price").ascending());
        Double bookPrice;

        try {
            bookPrice = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Invalid format!!");
        }

        return new ResponseEntity<>(bookService.getBooksLessThan(bookPrice, pageable), HttpStatus.OK);
    }
    @GetMapping("/author-price")
    public ResponseEntity<List<BookDto>> getAllBooksAuthorContainingAndPriceLessThan(@RequestParam String author,
                                                                                     @RequestParam Double price) {
        return new ResponseEntity<>(bookService.getAllBooksAuthorContainingAndPriceLessThan(author, price), HttpStatus.OK);
    }

    @GetMapping("/stock-price")
    public ResponseEntity<Page<BookDto>> getAllBooksStockLessThanAndPriceLessThan(@RequestParam Integer stock,
                                                                                  @RequestParam Double price,
                                                                                  @PageableDefault(sort = "stock") Pageable pageable) {

        //Pageable pageable = PageRequest.of(page - 1, size, Sort.by("stock").ascending());
        return new ResponseEntity<>(bookService.getAllBooksStockLessThanAndPriceLessThan(stock, price, pageable), HttpStatus.OK);
    }

    @GetMapping("/search-username")
    public ResponseEntity<Page<BookDto>> getAllBooksByUsername(@RequestParam String username,
                                                               @PageableDefault Pageable pageable) {
        return new ResponseEntity<>(bookService.getAllBookByUsername(username, pageable), HttpStatus.OK);
    }

    @GetMapping("/get-title")
    public ResponseEntity<List<BookDto>> getAllBooksByTitle(@RequestParam String title) {

        return new ResponseEntity<>(bookService.getAllBooksByTitle(title), HttpStatus.OK);
    }

}
