package com.triple.replymileageapi.useracct;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@Getter
@Builder
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@ToString // for test
public class UserAcct {

    @Id
    @Column(name="user_id", columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(name="user_mileage")
    @ColumnDefault("0")
    private Integer mileage;

    @CreationTimestamp
    @Column(nullable = false, length = 20, updatable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(length = 20)
    private LocalDateTime updated;
}
