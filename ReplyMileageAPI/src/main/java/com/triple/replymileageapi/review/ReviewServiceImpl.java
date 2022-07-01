package com.triple.replymileageapi.review;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepo reviewRepo;

    ReviewServiceImpl(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    @Override
    public Review insertReview(Review review) {

        reviewRepo.save(review);

        return reviewRepo.findByReviewId(review.getReviewId()).get(0);
    }

    @Override
    public UUID modReview(Review review) {
        return null;
    }

    @Override
    public UUID deleteReview(Review review) {
        return null;
    }
}
