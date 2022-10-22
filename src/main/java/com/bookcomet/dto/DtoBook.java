package com.bookcomet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoBook {
    private Long id;
    @NotNull(message = "{name.required}")
    private String name;
    @NotNull(message = "{author.required}")
    private String author;
    @NotNull(message = "{publisher.required}")
    private String publisher;
    private Integer yearOfPublication;
    private String summary;
}
