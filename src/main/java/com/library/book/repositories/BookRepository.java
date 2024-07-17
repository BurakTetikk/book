package com.library.book.repositories;

import com.library.book.entity.BookEntity;
import com.library.book.entity.UserEntity;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByTitleContaining(String title);

    List<BookEntity> findByAuthorContaining(String title);

    List<BookEntity> findByUser(UserEntity user);

    BookEntity findBookById(Long id);

    List<BookEntity> findByUserAndTitleContaining(UserEntity user, String title);

    Page<BookEntity> findBooksByTitleContaining(String title, Pageable pageable);


}
