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
        name = "roles",
        uniqueConstraints = {
                @UniqueConstraint(name = "roles_authority_unique", columnNames = "authority")
        }
)
public class Role {
    @Id
    @SequenceGenerator(
            name = "roles_sequence",
            sequenceName = "roles_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "roles_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "authority",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String authority;

    public Role(final String authority) {
        this.authority = authority;
    }
}
