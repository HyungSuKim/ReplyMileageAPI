package com.triple.replymileageapi.mileagehist;

import com.triple.replymileageapi.review.Review;
import com.triple.replymileageapi.useracct.UserAcct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MileageHistRepo  extends JpaRepository<MileageHist, Long> {
    List<MileageHist> findByReview(Review review);
    MileageHist findByReviewAndPlaceIdAndUseFlag(Review review, String placeId, String useFlag);
    List<MileageHist> findByReviewAndPlaceIdAndUseFlagOrderByCreatedDesc(Review review, String placeId, String useFlag);
    List<MileageHist> findByUserAcctOrderByCreatedDesc(UserAcct userAcct);
    List<MileageHist> findByUserAcctAndPlaceIdOrderByCreatedDesc(UserAcct userAcct, String placeId);
    List<MileageHist> findByUserAcctAndPlaceIdAndReviewOrderByCreatedDesc(UserAcct userAcct, String placeId, Review review);
}
