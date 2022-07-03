package com.triple.replymileageapi.mileagehist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.triple.replymileageapi.review.Review;
import com.triple.replymileageapi.useracct.UserAcct;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@ToString // for test
public class MileageHist {

    @Id
    @GeneratedValue
    private Long   id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false, updatable = false)
    private UserAcct userAcct;

    private String placeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="review_id", nullable = false, updatable = false)
    private Review review;

    private Integer mileage;

    private Integer mileageChange;

    private String  action;

    @JsonIgnore
    @Setter
    @Column(columnDefinition = "CHAR(1)")
    @ColumnDefault("'Y'")
    private String useFlag;

    @JsonIgnore
    @Column(columnDefinition = "CHAR(1)")
    @ColumnDefault("'N'")
    private String bonusFlag;

    @JsonIgnore
    @CreationTimestamp
    @Column(nullable = false, length = 20, updatable = false)
    private LocalDateTime created;
}
