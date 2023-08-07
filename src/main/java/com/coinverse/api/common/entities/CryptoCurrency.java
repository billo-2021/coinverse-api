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
@Table(name = "crypto_currencies")
public class CryptoCurrency {
    @Id
    @SequenceGenerator(
            name = "crypto_currencies_sequence",
            sequenceName = "crypto_currencies_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "crypto_currencies_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "crypto_currency_id", referencedColumnName = "id", nullable = false)
    private Currency currency;

    @Column(
            name = "circulating_supply",
            nullable = false
    )
    private Long circulatingSupply;

    public CryptoCurrency(final Currency currency,
                          final Long circulatingSupply) {
        this.currency = currency;
        this.circulatingSupply = circulatingSupply;
    }
}
