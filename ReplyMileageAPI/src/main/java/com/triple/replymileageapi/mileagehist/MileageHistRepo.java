package com.triple.replymileageapi.mileagehist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MileageHistRepo  extends JpaRepository<MileageHist, Long> {
    List<MileageHist> findByReviewId(String reviewId);
    MileageHist findByReviewIdAndPlaceIdAndUseFlag(String replyId, String placeId, String useFlag);
    List<MileageHist> findByReviewIdAndPlaceIdAndUseFlagOrderByCreatedDesc(String replyId, String placeId, String useFlag);
    List<MileageHist> findByUserIdOrderByCreatedDesc(String userId);
    List<MileageHist> findByUserIdAndPlaceIdOrderByCreatedDesc(String userId, String placeId);
    List<MileageHist> findByUserIdAndPlaceIdAndReviewIdOrderByCreatedDesc(String userId, String placeId, String reviewId);
}
