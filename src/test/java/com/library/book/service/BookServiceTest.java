package com.library.book.service;

import com.library.book.dto.BookDto;
import com.library.book.entity.BookEntity;
import com.library.book.mapper.MapperUtil;
import com.library.book.repositories.BookRepository;
import com.library.book.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MapperUtil mapperUtil;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testGetBookById() throws Exception {
        BookEntity mockBook = new BookEntity(
                87L,
                "Test Book",
                "Test Author",
                "98765345678",
                1687,
                12,
                null
        );

        BookDto mockBookDto = new BookDto(
                87L,
                "Test Book",
                "Test Author",
                "98765345678",
                1687,
                12,
                null
        );
        when(bookRepository.findById(87L)).thenReturn(Optional.of(mockBook));
        doReturn(mockBookDto).when(mapperUtil).convert(eq(mockBook), any(BookDto.class));

        BookDto result = bookService.getBookById(87L);

        assertNotNull(result);
        assertEquals(87L, result.getId());
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository).findById(87L);
        verify(mapperUtil).convert(eq(mockBook), any(BookDto.class));

    }


    @Test
    public void testDeleteBook() throws Exception {

        Long bookId = 79L;


        //Mock davranışı tanımla
        doNothing().when(bookRepository).deleteById(bookId);

        //Metodu çağır
        bookService.deleteBook(bookId);

        //deleteById metodun repositoryde çağrıldığını doğrula
        verify(bookRepository).deleteById(bookId);
    }


/*    @Test
    public void testSearchBooksByTitle() throws Exception {

        BookEntity mockBook = new BookEntity(
                87L,
                "Test Book",
                "Test Author",
                "98765345678",
                1687,
                12,
                null
        );

        Pageable pageable = PageRequest.of(1, 2);

            when(bookRepository.findBooksByTitleContaining("Book", pageable)).thenReturn((Page<BookEntity>) mockBook);

            Page<BookDto> bookEntity = bookService.searchBooksByTitle(mockBook.getTitle(), pageable);

            verify(bookRepository.findBooksByTitleContaining(mockBook.getTitle(), pageable));
    }*/
}
