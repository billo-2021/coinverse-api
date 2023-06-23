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
        name = "currency_pairs",
        uniqueConstraints = {
                @UniqueConstraint(name = "currency_pairs_name_unique", columnNames = "name")
        }
)
public class CurrencyPair {
    @Id
    @SequenceGenerator(
            name = "currency_pairs_sequence",
            sequenceName = "currency_pairs_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "currency_pairs_sequence"
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
    private Currency baseCurrency;

    @ManyToOne
    @JoinColumn(name = "quote_currency_id", referencedColumnName = "id", nullable = false)
    private Currency quoteCurrency;

    public CurrencyPair(final String name, final Currency baseCurrency, final Currency quoteCurrency) {
        this.name = name;
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
    }
}
