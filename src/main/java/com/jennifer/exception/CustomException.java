package com.jennifer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomException  extends RuntimeException{
    private String message;
    private String code;
    private String status;


    public CustomException(String message) {
        this.message = message;
    }
}
