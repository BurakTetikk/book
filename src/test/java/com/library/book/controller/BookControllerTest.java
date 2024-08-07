package com.library.book.controller;

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
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

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

    /*    BookDto mockBook = new BookDto(
                87L,
                "Test Book",
                "Test Author",
                "98765345678",
                1687,
                12,
                null
        );*/

        BookDto mockBookDto = new BookDto();
        mockBookDto.setAuthor("Test Author");
        mockBookDto.setId(87L);
        mockBookDto.setTitle("Test Book");

        when(bookRepository.findById(87L)).thenReturn(Optional.of(mockBook));
        doReturn(mockBookDto).when(mapperUtil).convert(eq(mockBook), any(BookDto.class));

        BookDto result = bookService.getBookById(87L);

        assertNotNull(result);
        assertEquals(87L, result.getId());
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository).findById(87L);
        verify(mapperUtil).convert(eq(mockBook), any(BookDto.class));

    }
}
