package com.triple.replymileageapi.reply;

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
public class Reply {
    @Id
    @Column(name="reply_id", columnDefinition = "BINARY(16)")
    private UUID replyId;

    @Column(name="place_id", columnDefinition = "BINARY(16)")
    private UUID placeId;

    @Column( nullable = false, columnDefinition = "NVARCHAR(4000)")
    private String content;

    @Column(name="reply_useflag", columnDefinition = "CHAR(1)")
    @ColumnDefault("'Y'")
    private String useFlag;

    @CreationTimestamp
    @Column(nullable = false, length = 20, updatable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(length = 20)
    private LocalDateTime updated;
}
