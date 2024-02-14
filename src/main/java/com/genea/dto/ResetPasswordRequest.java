package com.genea.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    @NotNull
    private String password;
}
