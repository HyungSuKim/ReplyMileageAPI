package com.triple.replymileageapi.controller.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Setter
@Getter
public class RequestReviewModel {
    private String       type;
    private String       action;
    private String       reviewId;
    private String       content;
    private List<String> attachedPhotoIds;
    private String       userId;
    private String       placeId;
}
