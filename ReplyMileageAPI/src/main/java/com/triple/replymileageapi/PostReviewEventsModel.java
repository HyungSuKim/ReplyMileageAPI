package com.triple.replymileageapi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@ToString
@Setter
@Getter
public class PostReviewEventsModel {
    private String       type;
    private String       action;
    private String       reviewId;
    private String       content;
    private List<String> attachedPhotoIds;
    private String       userId;
    private String       placeId;
}
