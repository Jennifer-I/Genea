package com.genea.dto;

import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ForgotPasswordRequest {
    private String email;
}
