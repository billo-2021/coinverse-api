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
@Table(name = "crypto_currency_exchange_rates")
public class CryptoCurrencyExchangeRate {
    @Id
    @SequenceGenerator(
            name = "crypto_currency_exchange_rates_sequence",
            sequenceName = "crypto_currency_exchange_rates_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "crypto_currency_exchange_rates_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "currency_pair_id", referencedColumnName = "id", nullable = false)
    private CryptoCurrencyPair currencyPair;

    @Column(
            name = "bid_rate",
            nullable = false
    )
    private BigDecimal bidRate;

    @Column(
            name = "ask_rate",
            nullable = false
    )
    private BigDecimal askRate;

    @Column(
            name = "created_at",
            nullable = false
    )
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column(
            name = "time_to_live",
            nullable = false
    )
    private Integer timeToLive;

    @Column(
            name = "updated_at"
    )
    @UpdateTimestamp
    private OffsetDateTime updateAt;

    public CryptoCurrencyExchangeRate(final CryptoCurrencyPair currencyPair,
                                      final BigDecimal bidRate,
                                      final BigDecimal askRate,
                                      final OffsetDateTime createdAt,
                                      final Integer timeToLive,
                                      final OffsetDateTime updateAt) {
        this.currencyPair = currencyPair;
        this.bidRate = bidRate;
        this.askRate = askRate;
        this.createdAt = createdAt;
        this.timeToLive = timeToLive;
        this.updateAt = updateAt;
    }
}
