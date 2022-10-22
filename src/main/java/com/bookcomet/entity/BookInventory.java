package com.bookcomet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookInventory {
    @Id
    @GeneratedValue
    private Long id;
    private Long quantity;
    @OneToOne
    private Book book;
}
