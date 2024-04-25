package com.jennifer.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor

public class UserNotFoundException extends RuntimeException{

    private String message;
    private String code;
    private String status;

    public UserNotFoundException(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public UserNotFoundException(String message) {
        this.message = message;
    }


}
