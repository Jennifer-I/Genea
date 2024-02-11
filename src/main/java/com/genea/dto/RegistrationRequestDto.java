package com.genea.dto;

import com.genea.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String Password;
    private Role role;
}
