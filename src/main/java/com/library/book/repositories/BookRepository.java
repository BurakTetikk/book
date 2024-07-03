package com.library.book.repositories;

import com.library.book.models.Book;
import com.library.book.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContaining(
            String title
    );
    List<Book> findByAuthorContaining(
            String title
    );
    List<Book> findByUser(
            User user
    );
    List<Book> findByUserAndTitleContaining(
            User user,
            String title
    );


}
