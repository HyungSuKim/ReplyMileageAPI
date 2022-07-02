package com.triple.replymileageapi.review;

import com.triple.replymileageapi.RequestReviewModel;

import java.util.UUID;

public interface ReviewService {
    boolean isUserUniqueReviewPlace(RequestReviewModel model);
    Integer getPlaceReviewCount(RequestReviewModel model);
    Review getPreviousReview(RequestReviewModel model);
    Review insertReview(RequestReviewModel model);
    Review updateReviewUseFlag(RequestReviewModel model);
}
