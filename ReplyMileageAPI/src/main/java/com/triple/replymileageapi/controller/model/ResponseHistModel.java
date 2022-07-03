package com.triple.replymileageapi.controller.model;

import com.triple.replymileageapi.useracct.UserAcct;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseHistModel {
    private Long   id;

    private String userId;

    private String placeId;

    private String reviewId;

    private Integer mileage;

    private Integer mileageChange;

    private String  action;
}
