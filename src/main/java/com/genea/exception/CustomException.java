package com.genea.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CustomException  extends RuntimeException{
    private String message;
    private String code;
    private String status;


    public CustomException(String message) {
        this.message = message;
    }
}
