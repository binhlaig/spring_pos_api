package com.binhlaig.pos.auth;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "users")

public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 80)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 30)
    private Set<Role> roles = new HashSet<>();

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "avatar_path")
    private String avatarPath;


    @PrePersist
    void onCreate() {
        createdAt = OffsetDateTime.now();
        if (active == null) active = true;
    }
}
