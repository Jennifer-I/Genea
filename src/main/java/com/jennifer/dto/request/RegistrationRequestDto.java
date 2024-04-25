package com.jennifer.dto.request;

import com.jennifer.enums.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegistrationRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String Password;
    private String phoneNumber;
    private Role role;


}
