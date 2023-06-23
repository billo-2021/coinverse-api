package com.coinverse.api.common.entities;

import com.fasterxml.jackson.annotation.JsonRawValue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @SequenceGenerator(
            name = "payments_sequence",
            sequenceName = "payments_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "payments_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "amount",
            nullable = false
    )
    private BigDecimal amount;

//    @ManyToOne
//    @JoinColumn(name = "exchange_rate_id", referencedColumnName = "id", nullable = false)
//    private CurrencyExchangeRate exchangeRate;

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id", nullable = false)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", referencedColumnName = "id", nullable = false)
    private PaymentMethod method;

    @ManyToOne
    @JoinColumn(name = "action_id", referencedColumnName = "id", nullable = false)
    private PaymentAction action;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    @Column(name = "meta_data", columnDefinition = "json")
    @JsonRawValue
    private String metaData;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    private PaymentStatus status;

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

    public Payment(final BigDecimal amount,
                   final Currency currency,
                   final PaymentMethod method,
                   final PaymentAction action,
                   final Account account,
                   final String metaData,
                   final PaymentStatus status,
                   final OffsetDateTime createdAt,
                   final OffsetDateTime updatedAt) {
        this.amount = amount;
        this.currency = currency;
        this.method = method;
        this.action = action;
        this.account = account;
        this.metaData = metaData;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
