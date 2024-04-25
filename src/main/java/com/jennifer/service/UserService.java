package com.jennifer.service;

import com.jennifer.dto.request.ForgotPasswordRequest;
import com.jennifer.dto.request.LoginRequest;
import com.jennifer.dto.request.RegistrationRequestDto;
import com.jennifer.dto.request.ResetPasswordRequest;
import com.jennifer.dto.response.ApiResponse;
import com.jennifer.dto.response.LoginResponse;
import com.jennifer.dto.response.UserResponseDto;
import com.jennifer.exception.UserNotFoundException;

public interface UserService {
    ApiResponse<UserResponseDto> createCustomer(RegistrationRequestDto request) throws InterruptedException, UserNotFoundException;



    ApiResponse<String> verifyEmail(String confirmationToken);

    ApiResponse<String> resendVerificationToken(String email) throws InterruptedException;


    ApiResponse<String> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws InterruptedException;

    ApiResponse<String> resetPassword( String confirmationToken, ResetPasswordRequest resetPasswordRequest);

    ApiResponse<String> createAdmin(RegistrationRequestDto registrationRequestDto) throws InterruptedException;

    ApiResponse<LoginResponse> loginUser(LoginRequest loginRequest) throws UserNotFoundException;
}
