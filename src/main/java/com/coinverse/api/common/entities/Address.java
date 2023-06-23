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
        name = "addresses"
)
public class Address {
    @Id
    @SequenceGenerator(
            name = "account_verification_methods_sequence",
            sequenceName = "account_verification_methods_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_verification_methods_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "address_line",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String addressLine;

    @Column(
            name = "street",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String street;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private Country country;

    @Column(
            name = "province",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String province;

    @Column(
            name = "city",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String city;

    @Column(
            name = "postal_code",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String postalCode;

    public Address(
            final String addressLine,
            final String street,
            final Country country,
            final String province,
            final String city,
            final String postalCode
    ) {
        this.addressLine = addressLine;
        this.street = street;
        this.country = country;
        this.province = province;
        this.city = city;
        this.postalCode = postalCode;
    }
}
