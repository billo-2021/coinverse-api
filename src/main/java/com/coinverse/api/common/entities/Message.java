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
        name = "messages"
)
public class Message {
    @Id
    @SequenceGenerator(
            name = "messages_sequence",
            sequenceName = "messages_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "messages_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    private MessagingGroup recipients;

    @Column(
            name = "subject",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String subject;

    @Column(
            name = "content",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String content;

    @ManyToOne
    @JoinColumn(name = "message_status_id", referencedColumnName = "id", nullable = false)
    private MessageStatus status;

    @Column(
            name = "createdAt"
    )
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column(
            name = "updatedAt"
    )
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    public Message(
            final MessagingGroup recipients,
            final String subject,
            final String content,
            final MessageStatus status,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt,
            final Account account
    ) {
        this.recipients = recipients;
        this.subject = subject;
        this.content = content;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.account = account;
    }
}
