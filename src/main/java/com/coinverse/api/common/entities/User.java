package com.coinverse.api.common.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_email_unique", columnNames = "email_address")
        }
)
public class User {
    @Id
    @SequenceGenerator(
            name = "users_sequence",
            sequenceName="users_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private long id;

    @Column(
            name = "email_address",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String emailAddress;

    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;

    @Column(
            name = "phone_number",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "preference_id", referencedColumnName = "id", nullable = false)
    private UserPreference preference;

    @Column(
            name = "created_at",
            nullable = false
    )
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column(
            name = "updated_at"
    )
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    public User(
            final String emailAddress,
            final String firstName,
            final String lastName,
            final String phoneNumber,
            final Account account,
            final Address address,
            final UserPreference preference,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt
    ) {
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.account = account;
        this.address = address;
        this.preference = preference;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
