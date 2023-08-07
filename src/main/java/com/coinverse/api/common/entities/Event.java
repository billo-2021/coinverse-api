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
        name = "events"
)
public class Event {
    @Id
    @SequenceGenerator(
            name = "events_sequence",
            sequenceName = "events_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "events_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_type_id", referencedColumnName = "id", nullable = false)
    private EventType type;

    @Column(
            name = "description",
            columnDefinition = "TEXT"
    )
    private String description;

    public Event(final EventType type, final String description) {
        this.type = type;
        this.description = description;
    }
}
