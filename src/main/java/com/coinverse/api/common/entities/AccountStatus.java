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
        name = "account_statuses",
        uniqueConstraints = {
                @UniqueConstraint(name = "account_statuses_name_unique", columnNames = "name")
        }
)
public class AccountStatus {
    @Id
    @SequenceGenerator(
            name = "account_statuses_sequence",
            sequenceName = "account_statuses_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_statuses_sequence"
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

    public AccountStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
