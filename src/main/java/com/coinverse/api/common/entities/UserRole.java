package com.coinverse.api.common.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_roles")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserRole {
    @Id
    @SequenceGenerator(name = "user_roles_sequence",
            sequenceName="user_roles_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_roles_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private long id;
    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;
}
