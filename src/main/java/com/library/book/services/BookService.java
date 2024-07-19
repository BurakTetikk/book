package com.library.book.services;

import com.library.book.dto.BookDto;
import com.library.book.dto.UserDto;
import com.library.book.entity.BookEntity;
import com.library.book.entity.UserEntity;
import com.library.book.exceptions.BadRequestException;
import com.library.book.exceptions.ResourceNotFoundException;
import com.library.book.mapper.MapperUtil;
import com.library.book.repositories.BookRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;//TODO neden UserRepository yok?

    private final MapperUtil mapperUtil;

    public BookDto saveBook(BookDto book) {
        BookEntity bookEntity = mapperUtil.convert(book, new BookEntity());
        bookRepository.save(bookEntity);
        return mapperUtil.convert(bookEntity, new BookDto());
    }

    public Page<BookDto> getAllBooks(Pageable pageable) {

        Page<BookEntity> books = bookRepository.findAll(pageable);

        // Burada mapper ile book entityyi bookdtoya convert edip dönüyoruz
        return books.map(bookEntity -> mapperUtil.convert(bookEntity, new BookDto()));
    }

    public BookDto getBookById(Long id) {

        if (id == null) {
            throw new BadRequestException("ID is empty or null!!");
        }

           BookEntity book = bookRepository
                   .findById(id)
                   .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

            return mapperUtil.convert(book, new BookDto());

    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Page<BookDto> searchBooksByTitle(String title, Pageable pageable) {

        Page<BookEntity> books = bookRepository.findByTitleContaining(title, pageable);

        if (books.isEmpty()) {
            throw new ResourceNotFoundException("Book not found with title: " + title);
        }

        return books.map(bookEntity -> mapperUtil.convert(bookEntity, new BookDto()));
    }

    public Page<BookDto> searchBooksByAuthor(String author, Pageable pageable) {

        Page<BookEntity> books = bookRepository.findByAuthorContaining(author, pageable);

        if (books.isEmpty()) {
            throw new ResourceNotFoundException("Book not found with author: " + author);
        }
        return books.map(bookEntity -> mapperUtil.convert(bookEntity, new BookDto()));
    }

    public Page<BookDto> getAllBooksByUser(UserDto userDto, Pageable pageable) {
        Page<BookEntity> books = bookRepository.findByUser(mapperUtil.convert(userDto, new UserEntity()), pageable);
        return books.map(bookEntity -> mapperUtil.convert(bookEntity, new BookDto()));
    }

    public Page<BookDto> searchBooksByUserAndTitle(UserDto userDto, String title, Pageable pageable) {
        Page<BookEntity> books = bookRepository.findByUserAndTitleContaining(mapperUtil.convert(userDto, new UserEntity()), title, pageable);
        return books.map(bookEntity -> mapperUtil.convert(bookEntity, new BookDto()));
    }

    public Page<BookDto> searchBooksByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<BookEntity> bookEntities= bookRepository.findBooksByTitleContaining(title, pageable);

        return bookEntities.map(bookEntity -> mapperUtil.convert(bookEntity, new BookDto()));
    }

    public Page<BookDto> getAllPage(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("title").ascending());

        Page<BookEntity> bookEntities = bookRepository.findAll(pageable);

        return bookEntities.map(bookEntity -> mapperUtil.convert(bookEntity, new BookDto()));
    }

    public Page<BookDto> getBooksLessThan(Double price, Pageable pageable) {
        Page<BookEntity> bookEntities = bookRepository.findByPriceLessThan(price, pageable);

        return bookEntities
                .map(bookEntity -> mapperUtil.convert(bookEntity, new BookDto()));
    }

    public List<BookDto> getAllBooksAuthorContainingAndPriceLessThan(String author, Double price) {

        List<BookEntity> bookEntities = bookRepository.findByAuthorContainingAndPriceLessThan(author, price);

        return bookEntities
                .stream()
                .map(bookEntity -> mapperUtil.convert(bookEntity, new BookDto()))
                .collect(Collectors.toList());

    }

    public Page<BookDto> getAllBooksStockLessThanAndPriceLessThan(Integer stock, Double price, Pageable pageable) {

        Page<BookEntity> bookEntities = bookRepository.findByStockLessThanAndPriceLessThan(stock, price, pageable);

        return bookEntities.map(bookEntity -> mapperUtil.convert(bookEntity, new BookDto()));
    }
}
