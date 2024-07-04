package com.library.book.repositories;

import com.library.book.entity.BookEntity;
import com.library.book.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByTitleContaining(String title);

    List<BookEntity> findByAuthorContaining(String title);

    List<BookEntity> findByUser(UserEntity user);

    List<BookEntity> findByUserAndTitleContaining(UserEntity user, String title);
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
