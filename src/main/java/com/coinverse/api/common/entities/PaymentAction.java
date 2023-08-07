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
        name = "payment_actions",
        uniqueConstraints = {
                @UniqueConstraint(name = "payment_actions_name_unique", columnNames = "name")
        }
)
public class PaymentAction {
    @Id
    @SequenceGenerator(
            name = "payment_actions_sequence",
            sequenceName = "payment_actions_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "payment_actions_sequence"
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

    public PaymentAction(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
