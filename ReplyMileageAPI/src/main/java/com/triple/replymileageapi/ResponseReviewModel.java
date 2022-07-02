package com.triple.replymileageapi;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseReviewModel {
    private String       userId;
    private String       placeId;
    private String       reviewId;
    private String       errorDtl;
}
