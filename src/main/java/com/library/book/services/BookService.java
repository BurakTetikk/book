package com.library.book.services;

import com.library.book.dto.BookDto;
import com.library.book.dto.UserDto;
import com.library.book.entity.BookEntity;
import com.library.book.entity.UserEntity;
import com.library.book.mapper.MapperUtil;
import com.library.book.repositories.BookRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<BookDto> getAllBooks() {

        List<BookEntity> books = bookRepository.findAll();

        // Burada mapper ile book entityyi bookdtoya convert edip dönüyoruz
        return books.stream().map(bookEntity -> mapperUtil.convert(bookEntity, new BookDto())).collect(Collectors.toList());
    }

    public BookDto getBookById(Long id) {

            BookEntity book = bookRepository.findBookById(id);

            if (book == null) {
                throw new RuntimeException("Book not found with id: " + id);
            }

            return mapperUtil.convert(book, new BookDto());

    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookDto> searchBooksByTitle(String title) {
        List<BookEntity> books = bookRepository.findByTitleContaining(title);

        return books.stream().map(bookEntity -> mapperUtil.convert(bookEntity, new BookDto())).collect(Collectors.toList());
    }

    public List<BookDto> searchBooksByAuthor(String author) {
        List<BookEntity> books = bookRepository.findByAuthorContaining(author);
        return books.stream().map(bookEntity -> mapperUtil.convert(bookEntity, new BookDto())).collect(Collectors.toList());
    }

    public List<BookDto> getAllBooksByUser(UserDto userDto) {
        List<BookEntity> books = bookRepository.findByUser(mapperUtil.convert(userDto, new UserEntity()));
        return books.stream().map(bookEntity -> mapperUtil.convert(bookEntity, new BookDto())).collect(Collectors.toList());
    }

    public List<BookDto> searchBooksByUserAndTitle(UserDto userDto, String title) {
        List<BookEntity> books = bookRepository.findByUserAndTitleContaining(mapperUtil.convert(userDto, new UserEntity()), title);
        return books.stream().map(bookEntity -> mapperUtil.convert(bookEntity, new BookDto())).collect(Collectors.toList());
    }

    public Page<BookDto> searchBooksByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<BookEntity> bookEntities= bookRepository.findBooksByTitleContaining(title, pageable);

        return bookEntities.map(bookEntity -> mapperUtil.convert(bookEntity, new BookDto()));
    }

    public Page<BookDto> getAllPage(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<BookEntity> bookEntities = bookRepository.findAll(pageable);

        return bookEntities.map(bookEntity -> mapperUtil.convert(bookEntity, new BookDto()));
    }
}
