package com.genea.entity;

import com.genea.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "AccountToken")
public class UserAccountToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String confirmationToken;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;


    public UserAccountToken(Long id, String confirmationToken) {
    }
}
