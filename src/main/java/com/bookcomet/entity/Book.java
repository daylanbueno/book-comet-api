package com.bookcomet.entity;

import com.bookcomet.BookType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_book")
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String author;
    private String publisher;
    private Integer yearOfPublication;
    private String summary;
    @Enumerated(EnumType.STRING)
    private BookType type;
    private String format;
}
