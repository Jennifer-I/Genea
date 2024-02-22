package com.genea.dto.request;

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
