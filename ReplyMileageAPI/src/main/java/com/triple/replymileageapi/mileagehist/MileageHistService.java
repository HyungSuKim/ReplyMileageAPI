package com.triple.replymileageapi.mileagehist;

import com.triple.replymileageapi.review.Review;
import com.triple.replymileageapi.useracct.UserAcct;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MileageHistService {
    MileageHist insertMileageHist(Review review, Integer mileage, Integer placeReViewCount, UserAcct userAcct);
    Integer insertMileageHist(MileageHist preMileageHist, Review review, Integer mileage);
    void insertMileageHist(MileageHist preMileageHist, Integer mileage);
    MileageHist updateMileageHist(Review review);
    List<MileageHist> getMileageHist(String userId);
    List<MileageHist> getMileageHist(String userId, String placeId);
    List<MileageHist> getMileageHist(String userId, String placeId, String reviewId);
}
