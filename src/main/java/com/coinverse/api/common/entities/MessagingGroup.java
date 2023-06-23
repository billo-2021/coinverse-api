package com.coinverse.api.common.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "messaging_groups")
public class MessagingGroup {
    @Id
    @SequenceGenerator(
            name = "messaging_groups_sequence",
            sequenceName = "messaging_groups_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "messaging_groups_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    Long id;

    @ManyToOne
    @JoinColumn(name = "created_by_id", referencedColumnName = "id", nullable = false)
    User createdBy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="massaging_group_members",
            joinColumns = {@JoinColumn(name = "messaging_group_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    private Set<User> members;

    @Column(
            name = "created_at",
            nullable = false
    )
    @CreationTimestamp
    private OffsetDateTime createAt;

    @Column(
            name = "updated_at"
    )
    @UpdateTimestamp
    private OffsetDateTime updateAt;
}
