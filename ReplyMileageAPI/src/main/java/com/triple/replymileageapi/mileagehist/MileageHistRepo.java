package com.triple.replymileageapi.mileagehist;

import com.triple.replymileageapi.place.Place;
import com.triple.replymileageapi.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MileageHistRepo  extends JpaRepository<MileageHist, Long> {
    List<MileageHist> findByReviewId(String reviewId);
    MileageHist findByReviewIdAndPlaceIdAndUseFlag(String replyId, String placeId, String useFlag);
    List<MileageHist> findByReviewIdAndPlaceIdAndUseFlagOrderByCreatedDesc(String replyId, String placeId, String useFlag);
    List<MileageHist> findByUserIdAndPlaceIdAndReviewIdOrderByCreatedDesc(String userId, String placeId, String reviewId);
}
