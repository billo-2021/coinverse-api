package com.coinverse.api.common.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(
        name = "account_tokens",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "account_tokens_account_token_type_id_account_id_unique",
                        columnNames = {"type_id", "account_id"})
        }
)
public class AccountToken {
    @Id
    @SequenceGenerator(
            name = "account_tokens_sequence",
            sequenceName = "account_tokens_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_tokens_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    private AccountTokenType type;

    @Column(
            name = "token",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String key;

    @Column(
            name = "expires_at",
            nullable = false
    )
    private OffsetDateTime expiresAt;

    @Column(
            name = "number_of_usage_attempts",
            nullable = false
    )
    private Integer numberOfUsageAttempts;

    @Column(
            name = "last_usage_attempt_at"
    )
    private OffsetDateTime lastUsageAttemptAt;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    public AccountToken(
            final AccountTokenType type,
            final String key,
            final OffsetDateTime expiresAt,
            final Integer numberOfUsageAttempts,
            final OffsetDateTime lastUsageAttemptAt,
            final Account account
    ) {
        this.type = type;
        this.key = key;
        this.expiresAt = expiresAt;
        this.numberOfUsageAttempts = numberOfUsageAttempts;
        this.lastUsageAttemptAt = lastUsageAttemptAt;
        this.account = account;
    }
}
