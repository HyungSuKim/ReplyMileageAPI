package com.triple.replymileageapi.review;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name="i_userid", columnList = "userId")
        ,@Index(name="i_placeid", columnList = "placeId")
})
@Getter
@Builder
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@ToString // for test
public class Review {
    @Id
    private String reviewId;

    private String placeId;

    private String userId;

    @Column(nullable = false, columnDefinition = "NVARCHAR(4000)")
    private String content;

    private String attachedPhotoIds;

    @Setter
    @Column(columnDefinition = "CHAR(1)")
    @ColumnDefault("'Y'")
    private String useFlag;

    @CreationTimestamp
    @Column(nullable = false, length = 20, updatable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(length = 20)
    private LocalDateTime updated;
}
