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
@Table(name = "crypto_transactions")
public class CryptoTransaction {
    @Id
    @SequenceGenerator(
            name = "crypto_transactions_sequence",
            sequenceName = "crypto_transactions_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "crypto_transactions_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "amount",
            nullable = false
    )
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "amount_currency_id", referencedColumnName = "id", nullable = false)
    private CryptoCurrency amountCurrency;

    @ManyToOne
    @JoinColumn(name = "exchange_rate_id", referencedColumnName = "id", nullable = false)
    private CryptoCurrencyExchangeRate exchangeRate;

    @ManyToOne
    @JoinColumn(name = "action_id", referencedColumnName = "id", nullable = false)
    private CryptoTransactionAction action;

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
    private CryptoTransactionStatus status;

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

    public CryptoTransaction(final BigDecimal amount,
                             final CryptoCurrency amountCurrency,
                             final CryptoCurrencyExchangeRate exchangeRate,
                             final CryptoTransactionAction action,
                             final Account account,
                             final Wallet sourceWallet,
                             final Wallet destinationWallet,
                             final CryptoTransactionStatus status,
                             final OffsetDateTime createdAt,
                             final OffsetDateTime updatedAt) {
        this.amount = amount;
        this.amountCurrency = amountCurrency;
        this.exchangeRate = exchangeRate;
        this.action = action;
        this.account = account;
        this.sourceWallet = sourceWallet;
        this.destinationWallet = destinationWallet;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
