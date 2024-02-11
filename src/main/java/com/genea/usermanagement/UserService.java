package com.genea.usermanagement;

import com.genea.dto.ApiResponse;
import com.genea.dto.RegistrationRequestDto;
import com.genea.dto.UserResponseDto;
import com.genea.exception.UserNotFoundException;

public interface UserService {
    ApiResponse<UserResponseDto> createCustomer(RegistrationRequestDto request) throws InterruptedException, UserNotFoundException;



    ApiResponse<String> confirmEmail(String confirmationToken);
}
