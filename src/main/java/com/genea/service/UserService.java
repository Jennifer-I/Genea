package com.genea.service;

import com.genea.dto.*;
import com.genea.exception.UserNotFoundException;

public interface UserService {
    ApiResponse<UserResponseDto> createCustomer(RegistrationRequestDto request) throws InterruptedException, UserNotFoundException;



    ApiResponse<String> verifyEmail(String confirmationToken);

    ApiResponse<String> resendVerificationToken(String email) throws InterruptedException;


    ApiResponse<String> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws InterruptedException;

    ApiResponse<String> resetPassword( String confirmationToken, ResetPasswordRequest resetPasswordRequest);

    ApiResponse<String> createAdmin(RegistrationRequestDto registrationRequestDto) throws InterruptedException;
}
