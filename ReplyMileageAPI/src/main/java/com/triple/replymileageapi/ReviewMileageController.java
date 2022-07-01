package com.triple.replymileageapi;

import com.triple.replymileageapi.review.Review;
import com.triple.replymileageapi.review.ReviewService;
import com.triple.replymileageapi.useracct.UserAcct;
import com.triple.replymileageapi.useracct.UserAcctService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log
@RestController
public class ReviewMileageController {

    private final UserAcctService userAcctService;
    private final ReviewService reviewService;

    public ReviewMileageController(UserAcctService userAcctService, ReviewService reviewService) {
        this.userAcctService = userAcctService;
        this.reviewService = reviewService;
    }

    @PostMapping("/events")
    public ResponseEntity review(@RequestBody PostReviewEventsModel model){
        //System.out.println(model.toString());
        //log.info(model.toString());

        switch (model.getAction()) {
            case "ADD" :
                insert(model);
                break;
            case "MOD" :
                update(model);
                break;
            case "DELETE" :
                delete(model);
                break;
            default:
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity insert(PostReviewEventsModel model) {
        log.info(model.toString());

        UserAcct userAcct = UserAcct.builder()
                .userId(model.getUserId())
                .mileage(0)
                .build();

        userAcct = userAcctService.makeUserAcct(userAcct);

        log.info(userAcct.toString());

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

        log.info(model.getPlaceId().toString());
/*
        List<AttachedPhoto> attachedPhotoIds = new ArrayList<>();
        for (UUID photoId : model.getAttachedPhotoIds()) {
            AttachedPhoto attachedPhoto = AttachedPhoto.builder()
                    .attachedPhotoId(photoId)
                    .review(review)
                    .build();
            attachedPhotoIds.add(attachedPhoto);
            attachedPhotoRepo.save(attachedPhoto);
        }
*/

        Review result = reviewService.insertReview(review);

        log.info(result.toString());

        return new ResponseEntity(HttpStatus.CREATED);
    }

    public ResponseEntity update(PostReviewEventsModel model) {
        return new ResponseEntity(HttpStatus.CREATED);
    }

    public ResponseEntity delete(PostReviewEventsModel model) {
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
