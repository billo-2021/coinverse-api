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
        name = "currency_types"
)
public class CurrencyType {
    @Id
    @SequenceGenerator(
            name = "currency_types_sequence",
            sequenceName = "account_token_types_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "currency_types_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

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

    public CurrencyType(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
