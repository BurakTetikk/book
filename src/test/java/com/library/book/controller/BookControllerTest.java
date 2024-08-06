package com.library.book.controller;

import com.library.book.controllers.BookController;
import com.library.book.dto.BookDto;
import com.library.book.entity.BookEntity;
import com.library.book.entity.UserEntity;
import com.library.book.mapper.MapperUtil;
import com.library.book.services.BookService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
@WebMvcTest(BookController.class)
public class BookControllerTest {

    //@Autowired
    private final MockMvc mockMvc;

    @MockBean
    private BookService bookService;

/*
    @MockBean
    private MapperUtil mapperUtil;*/


    @Test
    public void testGetBookById() throws Exception {
/*        BookEntity bookEntity = new BookEntity(
                87L,
                "Test Book",
                "Test Author",
                "98765345678",
                1687,
                12,
                null
        );*/

        //BookDto bookDto = mapperUtil.convert(bookEntity, new BookDto());

        BookDto bookDto = new BookDto(
                87L,
                "Test Book",
                "Test Author",
                "98765345678",
                1687,
                12,
                null
        );

        given(bookService.getBookById(87L)).willReturn(bookDto);

        mockMvc.perform(get("/api/books/87"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));



    }
}
