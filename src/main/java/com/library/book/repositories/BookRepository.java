package com.library.book.repositories;

import com.library.book.entity.BookEntity;
import com.library.book.entity.UserEntity;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Page<BookEntity> findByTitleContaining(String title, Pageable pageable);

    Page<BookEntity> findByAuthorContaining(String title, Pageable pageable);

    List<BookEntity> findByUser(UserEntity user);

    BookEntity findBookById(Long id);

    List<BookEntity> findByUserAndTitleContaining(UserEntity user, String title);

    Page<BookEntity> findBooksByTitleContaining(String title, Pageable pageable);

    Page<BookEntity> findByPriceLessThan(Double price, Pageable pageable);

    //SQL Filter
    @Query("select b from BookEntity b where b.author like %:author% and b.price < :price")
    List<BookEntity> findByAuthorContainingAndPriceLessThan(@Param("author") String author, @Param("price") Double price);
    @Query("select b from BookEntity b where b.stock < :stock and b.price < :price")
    Page<BookEntity> findByStockLessThanAndPriceLessThan(@Param("stock") Integer stock, @Param("price") Double price, Pageable pageable);


}
