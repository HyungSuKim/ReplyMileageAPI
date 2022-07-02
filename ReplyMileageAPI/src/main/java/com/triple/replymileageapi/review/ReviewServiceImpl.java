package com.triple.replymileageapi.review;

import com.triple.replymileageapi.RequestReviewModel;
import com.triple.replymileageapi.useracct.UserAcct;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log
@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepo reviewRepo;

    ReviewServiceImpl(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    @Override
    public boolean isUserUniqueReviewPlace(RequestReviewModel model) {

        if(reviewRepo.findAllByUserIdAndPlaceIdAndUseFlag(model.getUserId(), model.getPlaceId(), "Y").size() == 0) {
            return true;
        }

        return false;
    }

    @Override
    public Integer getPlaceReviewCount(RequestReviewModel model) {
        Integer count = 0;

        count = reviewRepo.findAllByPlaceIdAndUseFlag(model.getPlaceId(), "Y").size();

        return count;
    }

    @Override
    public Review getPreviousReview(RequestReviewModel model) {
        return reviewRepo.findByReviewIdAndPlaceIdAndUseFlag(model.getReviewId(), model.getPlaceId(), "Y");
    }

    @Override
    public Review insertReview(RequestReviewModel model) {

        String attachedPhotoIds = new String();

        for(int i = 0; i < model.getAttachedPhotoIds().size(); i++) {
            if(i == 0) {
                attachedPhotoIds += model.getAttachedPhotoIds().get(i);
            } else {
                attachedPhotoIds += ";" + model.getAttachedPhotoIds().get(i);
            }
        }

        Review review = Review.builder()
                .reviewId(model.getReviewId())
                .placeId(model.getPlaceId())
                .userId(model.getUserId())
                .content(model.getContent())
                .attachedPhotoIds(attachedPhotoIds)
                .useFlag("Y")
                .build();

        reviewRepo.save(review);

        return reviewRepo.findByReviewId(review.getReviewId()).get(0);
    }

    @Override
    public Review updateReviewUseFlag(RequestReviewModel model) {
        Review previousReview = reviewRepo.findByReviewIdAndPlaceIdAndUseFlag(model.getReviewId(), model.getPlaceId(), "Y");

        previousReview.setUseFlag("N");

        reviewRepo.save(previousReview);

        return reviewRepo.findByReviewId(model.getReviewId()).get(0);
    }

}
