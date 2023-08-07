package com.coinverse.api.common.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @SequenceGenerator(
            name = "wallets_sequence",
            sequenceName = "wallets_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "wallets_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id", nullable = false)
    private Currency currency;

    @Column(
            name = "private_key",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String privateKey;

    @Column(
            name = "public_key",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String publicKey;

    @Column(
            name = "address",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String address;

    @Column(
            name = "balance",
            precision = 38,
            scale = 5,
            nullable = false
    )
    private BigDecimal balance;

    @Column(
            name = "created_at",
            nullable = false
    )
    @CreationTimestamp
    private OffsetDateTime createAt;

    @Column(
            name = "updated_at"
    )
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    public Wallet(final Account account,
                  final Currency currency,
                  final String privateKey,
                  final String publicKey,
                  final String address,
                  final BigDecimal balance,
                  final OffsetDateTime createAt,
                  final OffsetDateTime updatedAt) {
        this.account = account;
        this.currency = currency;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.address = address;
        this.balance = balance;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }
}
