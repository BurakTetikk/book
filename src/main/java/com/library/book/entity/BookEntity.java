package com.library.book.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;
    private String author;

    //@NaturalId
    private String ISBN;
    private double price;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
