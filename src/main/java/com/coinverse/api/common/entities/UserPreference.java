package com.coinverse.api.common.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user_preferences")
public class UserPreference {
    @Id
    @SequenceGenerator(
            name = "user_preferences_sequence",
            sequenceName = "user_preferences_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_preferences_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id", nullable = false)
    private Currency currency;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="user_preference_notification_channels",
            joinColumns = {@JoinColumn(name = "preference_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "notification_id", referencedColumnName = "id")}
    )
    private Set<NotificationChannel> notificationChannels;

    public UserPreference(
            final Currency currency,
            final Set<NotificationChannel> notificationChannels
    ) {
        this.currency = currency;
        this.notificationChannels = notificationChannels;
    }
}
