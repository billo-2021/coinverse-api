package com.coinverse.api.common.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user_account_events")
public class UserAccountEvent {
    @Id
    @SequenceGenerator(
            name = "user_account_events_sequence",
            sequenceName = "user_account_events_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_account_events_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    private Event event;

    @Column(
            name = "device_details",
            columnDefinition = "TEXT"
    )
    private String deviceDetails;

    @Column(
            name = "id_address",
            columnDefinition = "TEXT"
    )
    private String ipAddress;

    @Column(
            name = "created_at",
            nullable = false
    )
    @CreationTimestamp
    private OffsetDateTime createdAt;

    public UserAccountEvent(final Account account,
                            final Event event,
                            final String deviceDetails,
                            final String ipAddress,
                            final OffsetDateTime createdAt) {
        this.account = account;
        this.event = event;
        this.deviceDetails = deviceDetails;
        this.ipAddress = ipAddress;
        this.createdAt = createdAt;
    }
}
