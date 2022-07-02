package com.triple.replymileageapi.mileage;

import com.triple.replymileageapi.RequestReviewModel;
import com.triple.replymileageapi.review.Review;

public interface MileageService {
    //ins
    int getCalculateMileage(Review review, int placeReViewCount);
    //mod
    int getCalculateMileage(Review preReview, RequestReviewModel model);
}
