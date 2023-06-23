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
@Table(name = "stable_coins")
public class StableCoin {
    @Id
    @SequenceGenerator(
            name = "stable_coins_sequence",
            sequenceName = "stable_coins_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "stable_coins_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "crypto_currency_id", referencedColumnName = "id", nullable = false)
    private CryptoCurrency cryptoCurrency;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_id", referencedColumnName = "id", nullable = false)
    private Currency currency;

    public StableCoin(final CryptoCurrency cryptoCurrency, final Currency currency) {
        this.cryptoCurrency = cryptoCurrency;
        this.currency = currency;
    }
}
