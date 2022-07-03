package com.triple.replymileageapi.review;

import com.triple.replymileageapi.controller.model.RequestReviewModel;

public interface ReviewService {
    boolean isUniqueReviewId(RequestReviewModel model);
    boolean isUserUniqueReviewPlace(RequestReviewModel model);
    Integer getPlaceReviewCount(RequestReviewModel model);
    Review insertReview(RequestReviewModel model);
    Review updateReviewUseFlag(RequestReviewModel model);
}
