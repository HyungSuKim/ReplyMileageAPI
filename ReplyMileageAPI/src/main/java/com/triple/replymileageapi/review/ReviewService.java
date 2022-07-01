package com.triple.replymileageapi.review;

import java.util.UUID;

public interface ReviewService {
    Review insertReview(Review review);
    UUID modReview(Review review);
    UUID deleteReview(Review review);
}
