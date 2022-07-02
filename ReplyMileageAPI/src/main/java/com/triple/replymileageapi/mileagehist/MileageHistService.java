package com.triple.replymileageapi.mileagehist;

import com.triple.replymileageapi.review.Review;

public interface MileageHistService {
    MileageHist insertMileageHist(Review review, Integer mileage, Integer placeReViewCount);
    Integer insertMileageHist(MileageHist preMileageHist, Review review, Integer mileage);
    MileageHist updateMileageHist(Review review);
}
