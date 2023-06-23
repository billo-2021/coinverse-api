package com.coinverse.api.common.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(
        name = "accounts",
        uniqueConstraints = {
                @UniqueConstraint(name = "accounts_username_unique", columnNames = "username")
        }
)
public class Account {
    @Id
    @SequenceGenerator(
            name = "accounts_sequence",
            sequenceName = "accounts_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "accounts_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "username",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String username;

    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="account_roles",
            joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> roles;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    private AccountStatus status;

    @Column(
            name = "is_enabled",
            nullable = false,
            columnDefinition = "boolean default true"
    )
    private Boolean isEnabled;

    @Column(
            name = "login_attempts",
            nullable = false
    )
    private Integer loginAttempts;

    @Column(
            name = "last_login_at"
    )
    private OffsetDateTime lastLoginAt;

    @Column(
            name = "created_at",
            nullable = false
    )
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column(
            name = "updated_at"
    )
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    public Account(final String username, final String password, final AccountStatus status) {
        this.username = username;
        this.password = password;
        this.status = status;
    }
}
