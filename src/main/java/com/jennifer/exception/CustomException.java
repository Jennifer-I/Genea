package com.jennifer.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
