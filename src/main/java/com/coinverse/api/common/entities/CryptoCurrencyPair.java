package com.coinverse.api.common.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(
        name = "crypto_currency_pairs",
        uniqueConstraints = {
                @UniqueConstraint(name = "crypto_currency_pairs_name_unique", columnNames = "name"),
                @UniqueConstraint(name = "crypto_currency_pairs_base_quote_currency_unique",
                        columnNames = {"base_currency_id", "quote_currency_id"
                })
        }
)
public class CryptoCurrencyPair {
    @Id
    @SequenceGenerator(
            name = "crypto_currency_pairs_sequence",
            sequenceName = "crypto_currency_pairs_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "crypto_currency_pairs_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @ManyToOne
    @JoinColumn(name = "base_currency_id", referencedColumnName = "id", nullable = false)
    private CryptoCurrency baseCurrency;

    @ManyToOne
    @JoinColumn(name = "quote_currency_id", referencedColumnName = "id", nullable = false)
    private CryptoCurrency quoteCurrency;

    public CryptoCurrencyPair(final String name,
                              final CryptoCurrency baseCurrency,
                              final CryptoCurrency quoteCurrency) {
        this.name = name;
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
    }
}
