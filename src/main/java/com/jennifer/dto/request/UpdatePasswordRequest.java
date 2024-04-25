package com.jennifer.dto.request;

import lombok.*;


@Getter
@Setter

public class UpdatePasswordRequest {

    private String OldPassword;
    private String NewPassword;
    private String ConfirmPassword;
}
