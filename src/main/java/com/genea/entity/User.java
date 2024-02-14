package com.genea.entity;

import com.genea.enums.Role;
import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Enumerated(EnumType.ORDINAL)
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<Order> orders;
    Boolean isActive = false;

    private Boolean isVerified = false;
    private String fullName;




    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserAccountToken> userAccountToken;

}
