package com.genea.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse <T>{
    private int status;
    private String message;
    private T data;
    private HttpStatus httpStatus;

    public ApiResponse(int status, String message) {
        this.status = HttpStatus.OK.value();
        this.message = message;
    }
public ApiResponse(String message, T data){
        this.message = message;
        this.data = data;
}

    public ApiResponse(String errorMessage) {
    }

    public static <T> ApiResponse<T> OK(T data){
        return new ApiResponse<>("OK", data);
    }


}
