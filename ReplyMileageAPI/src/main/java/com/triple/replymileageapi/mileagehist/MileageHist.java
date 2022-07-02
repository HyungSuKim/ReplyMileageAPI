package com.triple.replymileageapi.mileagehist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name="i_userplacereview", columnList = "userId,placeId,reviewId")
})
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

    private String userId;

    private String placeId;

    private String reviewId;

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
