package com.triple.replymileageapi.mileage;

import com.triple.replymileageapi.RequestReviewModel;
import com.triple.replymileageapi.mileage.MileageService;
import com.triple.replymileageapi.review.Review;
import com.triple.replymileageapi.review.ReviewRepo;
import com.triple.replymileageapi.review.ReviewService;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Log
@Service
public class MileageServiceImpl implements MileageService {

    //insert
    @Override
    public int getCalculateMileage(Review review, int placeReViewCount) {
        int mileage = 0;

        if(placeReViewCount == 0)
            mileage += 1;

        if(review.getContent().length() != 0)
            mileage += 1;

        if(review.getAttachedPhotoIds().length() != 0)
            mileage += 1;

        return mileage;
    }

    //mod
    @Override
    public int getCalculateMileage(Review preReview, RequestReviewModel model) {
        int mileage = 0;

        if(preReview.getAttachedPhotoIds().length() == 0
        && model.getAttachedPhotoIds().size() != 0)
            mileage += 1;

        log.info("calmile1 : " + mileage);

        if(preReview.getAttachedPhotoIds().length() != 0
        && model.getAttachedPhotoIds().size() == 0)
            mileage -= 1;

        log.info("ids" + preReview.getAttachedPhotoIds());
        log.info("preattcedphotoleng" + preReview.getAttachedPhotoIds().length());
        log.info("attcedphotoleng" + model.getAttachedPhotoIds().size());
        log.info("calmile2 : " + mileage);

        return mileage;
    }
}
