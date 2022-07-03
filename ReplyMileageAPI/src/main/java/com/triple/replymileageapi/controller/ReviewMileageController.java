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
        //리뷰 아이디 중복 유효성 검사
        if(!reviewService.isUniqueReviewId(model)) {
            return new ResponseEntity<>(ResponseReviewModel.builder()
                    .userId(model.getUserId())
                    .placeId(model.getPlaceId())
                    .reviewId(model.getReviewId())
                    .errorDtl("Duplicate reviewId").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //장소 별 리뷰 1개 유효성 검사
        if(!reviewService.isUserUniqueReviewPlace(model)) {
            return new ResponseEntity<>(ResponseReviewModel.builder()
                    .userId(model.getUserId())
                    .placeId(model.getPlaceId())
                    .reviewId(model.getReviewId())
                    .errorDtl("Already Review for place").build(), HttpStatus.METHOD_NOT_ALLOWED);
        }

        //사용자 계좌 정보 획득
        UserAcct userAcct = userAcctService.makeUserAcct(model);

        //보너스 가능 여부를 확인하기 위한 장소 리뷰 개수 조회
        Integer placeReViewCount = 0;
        placeReViewCount = reviewService.getPlaceReviewCount(model);

        //리뷰 등록
        Review result = reviewService.insertReview(model);

        //마일리지 계산
        Integer mileage = mileageService.getCalculateMileage(result, placeReViewCount);

        //사용자 계좌 마일리지 적용
        userAcct = userAcctService.updateUserAcct(model, mileage);

        //마일리지 적용 기록
        MileageHist mileageHist = mileageHistService.insertMileageHist(result, mileage, placeReViewCount, userAcct);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseReviewModel.builder()
                        .userId(model.getUserId())
                        .placeId(model.getPlaceId())
                        .reviewId(model.getReviewId())
                        .errorDtl("Success").build());
    }

    public ResponseEntity update(RequestReviewModel model) {

        //이전 리뷰 획득 및 비 활성화
        Review previousReview = reviewService.updateReviewUseFlag(model);

        if(previousReview == null) {
            return new ResponseEntity<>(ResponseReviewModel.builder()
                    .userId(model.getUserId())
                    .placeId(model.getPlaceId())
                    .reviewId(model.getReviewId())
                    .errorDtl("There are no review to update").build(), HttpStatus.NOT_FOUND);
        }

        //수정 된 마일리지 계산
        Integer mileage = mileageService.getCalculateMileage(previousReview, model);
        //-1
        //리뷰 등록
        Review result = reviewService.insertReview(model);

        //이전 마일리지 획득 비 활성화 및 획득
        MileageHist preMileageHist = mileageHistService.updateMileageHist(previousReview);

        //마일리지 적용 기록 및 차 획득
        Integer mileageDiff = mileageHistService.insertMileageHist(preMileageHist, result, mileage);

        //사용자 계좌 마일리지 적용
        UserAcct userAcct = userAcctService.updateUserAcct(model, mileageDiff);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseReviewModel.builder()
                        .userId(model.getUserId())
                        .placeId(model.getPlaceId())
                        .reviewId(model.getReviewId())
                        .errorDtl("Success").build());
    }

    public ResponseEntity delete(RequestReviewModel model) {
        //이전 리뷰 획득 및 비 활성화
        Review previousReview = reviewService.updateReviewUseFlag(model);

        if(previousReview == null) {
            return new ResponseEntity<>(ResponseReviewModel.builder()
                    .userId(model.getUserId())
                    .placeId(model.getPlaceId())
                    .reviewId(model.getReviewId())
                    .errorDtl("There are no review to delete").build(), HttpStatus.NOT_FOUND);
        }

        //이전 마일리지 획득 비 활성화 및 획득
        MileageHist preMileageHist = mileageHistService.updateMileageHist(previousReview);

        Integer mileage = 0;
        mileage = mileage - preMileageHist.getMileage();

        //삭제 히스토리 기록
        mileageHistService.insertMileageHist(preMileageHist, mileage);

        //사용자 계좌 마일리지 적용
        UserAcct userAcct = userAcctService.updateUserAcct(model, mileage);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseReviewModel.builder()
                        .userId(model.getUserId())
                        .placeId(model.getPlaceId())
                        .reviewId(model.getReviewId())
                        .errorDtl("Success").build());
    }
}
