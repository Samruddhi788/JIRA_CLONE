package com.example.demo.model;
//bridge between roles ans auths 

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role_authorities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleAuthority {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles role;
}

