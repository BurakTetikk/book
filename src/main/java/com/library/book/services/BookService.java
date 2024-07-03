package com.library.book.services;

import com.library.book.dto.BookDto;
import com.library.book.entity.BookEntity;
import com.library.book.entity.UserEntity;
import com.library.book.mapper.MapperUtil;
import com.library.book.repositories.BookRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final MapperUtil mapperUtil;

    public BookEntity saveBook(BookEntity book) {
        return bookRepository.save(book);
    }

    public List<BookDto> getAllBooks() {

        List<BookEntity> books = bookRepository.findAll();

        // Burada mapper ile book entityyi bookdtoya convert edip dönüyoruz
        return books.stream().map(bookEntity -> mapperUtil.convert(bookEntity, new BookDto())).collect(Collectors.toList());
    }

    public BookEntity getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookEntity> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }

    public List<BookEntity> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContaining(author);
    }

    public List<BookEntity> getAllBooksByUser(UserEntity user) {
        return bookRepository.findByUser(user);
    }

    public List<BookEntity> searchBooksByUserAndTitle(UserEntity user, String title) {
        return bookRepository.findByUserAndTitleContaining(user, title);
    }
}
