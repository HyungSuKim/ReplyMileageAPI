package com.triple.replymileageapi.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepo extends JpaRepository<Review, String> {
    //insert
    List<Review> findByReviewId(String replyId);
    //insert
    List<Review> findByPlaceId(String placeId);
    //mod,del
    Review findByReviewIdAndPlaceIdAndUseFlag(String replyId, String placeId, String useFlag);
}
