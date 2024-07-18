package com.library.book.repositories;

import com.library.book.entity.UserEntity;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    List<UserEntity> findByUsernameContaining(String username);


}
