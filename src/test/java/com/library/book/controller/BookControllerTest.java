package com.library.book.controller;

import com.library.book.dto.BookDto;
import com.library.book.entity.BookEntity;
import com.library.book.repositories.BookRepository;
import com.library.book.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookRepository bookRepository;

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

        when(bookRepository.findBookById(87L)).thenReturn(mockBook);

        BookDto result = bookService.getBookById(87L);

        assertNotNull(result);
        assertEquals(87L, result.getId());
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository).findBookById(87L);

    }
}
