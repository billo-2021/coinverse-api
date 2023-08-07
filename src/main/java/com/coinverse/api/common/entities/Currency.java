package com.coinverse.api.common.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(
        name = "currencies"
)
public class Currency {
    @Id
    @SequenceGenerator(
            name = "currencies_sequence",
            sequenceName = "currencies_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "currencies_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    private CurrencyType type;

    @Column(
            name = "code",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String code;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @Column(
            name = "symbol",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String symbol;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="currency_countries",
            joinColumns = {@JoinColumn(name = "currency_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "country_id", referencedColumnName = "id")}
    )
    private Set<Country> countries;

    public Currency(final CurrencyType type,
                    final String code,
                    final String name,
                    final String symbol,
                    final Set<Country> countries) {
        this.type = type;
        this.code = code;
        this.name = name;
        this.symbol = symbol;
        this.countries = countries;
    }
}
