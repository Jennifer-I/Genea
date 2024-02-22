package com.genea.service;

import com.genea.dto.request.ForgotPasswordRequest;
import com.genea.dto.request.LoginRequest;
import com.genea.dto.request.RegistrationRequestDto;
import com.genea.dto.request.ResetPasswordRequest;
import com.genea.dto.response.ApiResponse;
import com.genea.dto.response.LoginResponse;
import com.genea.dto.response.UserResponseDto;
import com.genea.exception.UserNotFoundException;

public interface UserService {
    ApiResponse<UserResponseDto> createCustomer(RegistrationRequestDto request) throws InterruptedException, UserNotFoundException;



    ApiResponse<String> verifyEmail(String confirmationToken);

    ApiResponse<String> resendVerificationToken(String email) throws InterruptedException;


    ApiResponse<String> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws InterruptedException;

    ApiResponse<String> resetPassword( String confirmationToken, ResetPasswordRequest resetPasswordRequest);

    ApiResponse<String> createAdmin(RegistrationRequestDto registrationRequestDto) throws InterruptedException;

    ApiResponse<LoginResponse> loginUser(LoginRequest loginRequest) throws UserNotFoundException;
}
