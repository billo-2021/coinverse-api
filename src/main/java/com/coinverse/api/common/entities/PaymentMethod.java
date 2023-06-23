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
        name = "payment_methods",
        uniqueConstraints = {
                @UniqueConstraint(name = "payment_methods_name_unique", columnNames = "name")
        }
)
public class PaymentMethod {
    @Id
    @SequenceGenerator(
            name = "payment_methods_sequence",
            sequenceName = "payment_methods_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "payment_methods_sequence"
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

    public PaymentMethod(final String name) {
        this.name = name;
    }
}
