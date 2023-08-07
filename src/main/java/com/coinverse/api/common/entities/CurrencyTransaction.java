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
@Table(name = "currency_transactions")
public class CurrencyTransaction {
    @Id
    @SequenceGenerator(
            name = "currency_transactions_sequence",
            sequenceName = "crypto_transactions_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "currency_transactions_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "amount",
            precision = 38,
            scale = 5,
            nullable = false
    )
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id", nullable = false)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "action_id", referencedColumnName = "id", nullable = false)
    private CurrencyTransactionAction action;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "source_wallet_id", referencedColumnName = "id", nullable = false)
    private Wallet sourceWallet;

    @ManyToOne
    @JoinColumn(name = "destination_wallet_id", referencedColumnName = "id", nullable = false)
    private Wallet destinationWallet;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    private CurrencyTransactionStatus status;

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

    public CurrencyTransaction(final BigDecimal amount,
                               final Currency currency,
                               final CurrencyTransactionAction action,
                               final Account account,
                               final Wallet sourceWallet,
                               final Wallet destinationWallet,
                               final CurrencyTransactionStatus status,
                               final OffsetDateTime createdAt,
                               final OffsetDateTime updatedAt) {
        this.amount = amount;
        this.currency = currency;
        this.action = action;
        this.account = account;
        this.sourceWallet = sourceWallet;
        this.destinationWallet = destinationWallet;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
