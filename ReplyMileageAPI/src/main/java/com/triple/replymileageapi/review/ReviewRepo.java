package com.triple.replymileageapi.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, String> {
    //insert
    List<Review> findByReviewId(String replyId);

    List<Review> findAllByUserIdAndPlaceIdAndUseFlag(String userId, String placeId, String useFlag);

    List<Review> findAllByPlaceIdAndUseFlag(String placeId, String useFlag);

    //mod,del
    Review findByReviewIdAndPlaceIdAndUseFlag(String replyId, String placeId, String useFlag);
}
