package com.genea.dto;

import com.genea.enums.Role;
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
    private Role role;


}
