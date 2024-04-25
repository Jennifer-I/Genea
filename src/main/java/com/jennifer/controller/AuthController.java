package com.jennifer.controller;

import com.jennifer.dto.request.*;
import com.jennifer.dto.response.ApiResponse;
import com.jennifer.dto.response.LoginResponse;
import com.jennifer.dto.response.UserResponseDto;
import com.jennifer.exception.UserNotFoundException;
import com.jennifer.service.ProductService;
import com.jennifer.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor

@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> registerUser(@RequestBody RegistrationRequestDto request) throws UserNotFoundException, InterruptedException {
        ApiResponse<UserResponseDto> response = userService.createCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/verifyEmail")
    public ResponseEntity<ApiResponse<String>> verifyEmail(@RequestParam("confirmationToken") String confirmationToken) {
        ApiResponse<String> response = userService.verifyEmail(confirmationToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resendVerificationToken")
    public ResponseEntity<ApiResponse<String>> resendVerificationToken(@RequestParam("email") String email) throws InterruptedException {
        ApiResponse<String> response = userService.resendVerificationToken(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) throws InterruptedException {
        ApiResponse<String> response = userService.forgotPassword(forgotPasswordRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/resetPassword")
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestParam("confirmationToken") String confirmationToken, @Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        ApiResponse<String> response = userService.resetPassword(confirmationToken, resetPasswordRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<ApiResponse<String>> createAdmin(@RequestBody RegistrationRequestDto request) throws UserNotFoundException, InterruptedException {
        ApiResponse<String> response = userService.createAdmin(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        ApiResponse<LoginResponse> response = userService.loginUser(loginRequest);
        return ResponseEntity.ok(response);
    }


    }






