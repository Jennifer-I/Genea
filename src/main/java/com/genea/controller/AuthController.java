package com.genea.controller;

import com.genea.dto.ApiResponse;
import com.genea.dto.RegistrationRequestDto;
import com.genea.dto.UserResponseDto;
import com.genea.exception.UserNotFoundException;
import com.genea.usermanagement.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> registerUser(@RequestBody RegistrationRequestDto request) throws UserNotFoundException, InterruptedException {
        ApiResponse<UserResponseDto> response = userService.createCustomer(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    @PutMapping("/confirmEmail")
    public ResponseEntity<ApiResponse<String>> confirmEmail(@RequestParam("confirmationToken") String confirmationToken) {
        ApiResponse<String> response = userService.confirmEmail(confirmationToken);
        return ResponseEntity.ok(response);
    }


}




