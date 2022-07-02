package com.triple.replymileageapi;

import lombok.Builder;

@Builder
public class ResponseReviewModel {
    private String       userId;
    private String       placeId;
    private String       errorDtl;
}
