package com.triple.replymileageapi.review;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table
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

    /*@OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
    private List<AttachedPhoto> attachedPhotoIds;*/
    private String attachedPhotoIds;

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
