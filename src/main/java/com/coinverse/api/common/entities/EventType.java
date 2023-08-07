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
        name = "event_types",
        uniqueConstraints = {
                @UniqueConstraint(name = "event_types_name_unique", columnNames = "name")
        }
)
public class EventType {
    @Id
    @SequenceGenerator(
            name = "event_types_sequence",
            sequenceName = "event_types_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "event_types_sequence"
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

    public EventType(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
