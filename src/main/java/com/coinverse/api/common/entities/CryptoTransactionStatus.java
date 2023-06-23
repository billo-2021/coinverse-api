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
        name = "crypto_transaction_statuses",
        uniqueConstraints = {
                @UniqueConstraint(name = "crypto_transaction_statuses_unique", columnNames = "name")
        }
)
public class CryptoTransactionStatus {
    @Id
    @SequenceGenerator(
            name = "crypto_transaction_statuses_sequence",
            sequenceName = "crypto_transaction_statuses_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "crypto_transaction_statuses_sequence"
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

    public CryptoTransactionStatus(final String name) {
        this.name = name;
    }
}
