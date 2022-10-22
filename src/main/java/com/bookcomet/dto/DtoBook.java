package com.bookcomet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoBook {
    private Long id;
    private String name;
    private String author;
    private String publisher;
    private Integer yearOfPublication;
    private String summary;
}
