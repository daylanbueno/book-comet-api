package com.bookcomet.exceptions;

import lombok.Data;

@Data
public class ApiError {
    private String mensage;
    public ApiError(String mensage) {
        this.mensage = mensage;
    }

}
