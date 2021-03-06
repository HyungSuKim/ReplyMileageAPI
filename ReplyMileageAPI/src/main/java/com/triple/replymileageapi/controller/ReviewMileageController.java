package com.triple.replymileageapi.controller;

import com.triple.replymileageapi.controller.model.RequestReviewModel;
import com.triple.replymileageapi.controller.model.ResponseReviewModel;
import com.triple.replymileageapi.mileage.MileageService;
import com.triple.replymileageapi.mileagehist.MileageHist;
import com.triple.replymileageapi.mileagehist.MileageHistService;
import com.triple.replymileageapi.review.Review;
import com.triple.replymileageapi.review.ReviewService;
import com.triple.replymileageapi.useracct.UserAcct;
import com.triple.replymileageapi.useracct.UserAcctService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Log
@RestController
public class ReviewMileageController {

    private final UserAcctService userAcctService;
    private final ReviewService reviewService;
    private final MileageService mileageService;
    private final MileageHistService mileageHistService;

    public ReviewMileageController(
            UserAcctService userAcctService, ReviewService reviewService
            ,MileageService mileageService, MileageHistService mileageHistService) {
        this.userAcctService    = userAcctService;
        this.reviewService      = reviewService;
        this.mileageService     = mileageService;
        this.mileageHistService = mileageHistService;
    }

    @PostMapping("/events")
    public ResponseEntity<Object> review(@RequestBody RequestReviewModel model){
        switch (model.getAction()) {
            case "ADD" :
                return insert(model);
            case "MOD" :
                return update(model);
            case "DELETE" :
                return delete(model);
            default:
                return new ResponseEntity<>(ResponseReviewModel.builder()
                        .userId(model.getUserId())
                        .placeId(model.getPlaceId())
                        .reviewId(model.getReviewId())
                        .errorDtl("Not Defined Action.").build(),HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<Object> insert(RequestReviewModel model) {
        //?????? ????????? ?????? ????????? ??????
        if(!reviewService.isUniqueReviewId(model)) {
            return new ResponseEntity<>(ResponseReviewModel.builder()
                    .userId(model.getUserId())
                    .placeId(model.getPlaceId())
                    .reviewId(model.getReviewId())
                    .errorDtl("Duplicate reviewId").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //?????? ??? ?????? 1??? ????????? ??????
        if(!reviewService.isUserUniqueReviewPlace(model)) {
            return new ResponseEntity<>(ResponseReviewModel.builder()
                    .userId(model.getUserId())
                    .placeId(model.getPlaceId())
                    .reviewId(model.getReviewId())
                    .errorDtl("Already Review for place").build(), HttpStatus.METHOD_NOT_ALLOWED);
        }

        //????????? ?????? ?????? ??????
        UserAcct userAcct = userAcctService.makeUserAcct(model);

        //????????? ?????? ????????? ???????????? ?????? ?????? ?????? ?????? ??????
        Integer placeReViewCount = 0;
        placeReViewCount = reviewService.getPlaceReviewCount(model);

        //?????? ??????
        Review result = reviewService.insertReview(model);

        //???????????? ??????
        Integer mileage = mileageService.getCalculateMileage(result, placeReViewCount);

        //????????? ?????? ???????????? ??????
        userAcct = userAcctService.updateUserAcct(model, mileage);

        //???????????? ?????? ??????
        MileageHist mileageHist = mileageHistService.insertMileageHist(result, mileage, placeReViewCount, userAcct);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseReviewModel.builder()
                        .userId(model.getUserId())
                        .placeId(model.getPlaceId())
                        .reviewId(model.getReviewId())
                        .errorDtl("Success").build());
    }

    public ResponseEntity update(RequestReviewModel model) {

        //?????? ?????? ?????? ??? ??? ?????????
        Review previousReview = reviewService.updateReviewUseFlag(model);

        if(previousReview == null) {
            return new ResponseEntity<>(ResponseReviewModel.builder()
                    .userId(model.getUserId())
                    .placeId(model.getPlaceId())
                    .reviewId(model.getReviewId())
                    .errorDtl("There are no review to update").build(), HttpStatus.NOT_FOUND);
        }

        //?????? ??? ???????????? ??????
        Integer mileage = mileageService.getCalculateMileage(previousReview, model);
        //-1
        //?????? ??????
        Review result = reviewService.insertReview(model);

        //?????? ???????????? ?????? ??? ????????? ??? ??????
        MileageHist preMileageHist = mileageHistService.updateMileageHist(previousReview);

        //???????????? ?????? ?????? ??? ??? ??????
        Integer mileageDiff = mileageHistService.insertMileageHist(preMileageHist, result, mileage);

        //????????? ?????? ???????????? ??????
        UserAcct userAcct = userAcctService.updateUserAcct(model, mileageDiff);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseReviewModel.builder()
                        .userId(model.getUserId())
                        .placeId(model.getPlaceId())
                        .reviewId(model.getReviewId())
                        .errorDtl("Success").build());
    }

    public ResponseEntity delete(RequestReviewModel model) {
        //?????? ?????? ?????? ??? ??? ?????????
        Review previousReview = reviewService.updateReviewUseFlag(model);

        if(previousReview == null) {
            return new ResponseEntity<>(ResponseReviewModel.builder()
                    .userId(model.getUserId())
                    .placeId(model.getPlaceId())
                    .reviewId(model.getReviewId())
                    .errorDtl("There are no review to delete").build(), HttpStatus.NOT_FOUND);
        }

        //?????? ???????????? ?????? ??? ????????? ??? ??????
        MileageHist preMileageHist = mileageHistService.updateMileageHist(previousReview);

        Integer mileage = 0;
        mileage = mileage - preMileageHist.getMileage();

        //?????? ???????????? ??????
        mileageHistService.insertMileageHist(preMileageHist, mileage);

        //????????? ?????? ???????????? ??????
        UserAcct userAcct = userAcctService.updateUserAcct(model, mileage);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseReviewModel.builder()
                        .userId(model.getUserId())
                        .placeId(model.getPlaceId())
                        .reviewId(model.getReviewId())
                        .errorDtl("Success").build());
    }
}
