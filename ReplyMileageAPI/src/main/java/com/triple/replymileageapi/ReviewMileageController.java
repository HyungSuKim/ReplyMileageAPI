package com.triple.replymileageapi;

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
    public ResponseEntity<ResponseReviewModel> review(@RequestBody RequestReviewModel model){
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
    public ResponseEntity<ResponseReviewModel> insert(RequestReviewModel model) {
        //Response 다시 고민
        //장소 별 리뷰 1개 유효성 검사
        if(!reviewService.isUserUniqueReviewPlace(model)) {
            log.info("중복");
            return new ResponseEntity<>(ResponseReviewModel.builder()
                    .userId(model.getUserId())
                    .placeId(model.getPlaceId())
                    .errorDtl("Already Review for place").build(), HttpStatus.METHOD_NOT_ALLOWED);
        }


        log.info(model.toString());

        //사용자 계좌 정보 획득
        UserAcct userAcct = userAcctService.makeUserAcct(model);

        log.info(model.getPlaceId().toString());

        //보너스 가능 여부를 확인하기 위한 장소 리뷰 개수 조회
        Integer placeReViewCount = 0;
        placeReViewCount = reviewService.getPlaceReviewCount(model);

        //리뷰 등록
        Review result = reviewService.insertReview(model);
        log.info(result.toString());

        //마일리지 계산
        Integer mileage = mileageService.getCalculateMileage(result, placeReViewCount);

        //사용자 계좌 마일리지 적용
        userAcct = userAcctService.updateUserAcct(model, mileage);
        log.info(userAcct.toString());

        //마일리지 적용 기록
        MileageHist mileageHist = mileageHistService.insertMileageHist(result, mileage, placeReViewCount);
        log.info(mileageHist.toString());

        //return new ResponseEntity(HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseReviewModel.builder()
                        .userId(model.getUserId())
                        .placeId(model.getPlaceId())
                        .errorDtl("Success").build());
    }

    public ResponseEntity update(RequestReviewModel model) {

        //이전 리뷰 획득 및 비 활성화
        Review previousReview = reviewService.updateReviewUseFlag(model);
        log.info("UPDATE : " + previousReview.toString());

        log.info("before call calmile : " + previousReview.toString());
        //수정 된 마일리지 계산
        Integer mileage = mileageService.getCalculateMileage(previousReview, model);
        //-1
        //리뷰 등록
        Review result = reviewService.insertReview(model);
        log.info("UPDATE : " + result.toString());



        //이전 마일리지 획득 비 활성화 및 획득
        MileageHist preMileageHist = mileageHistService.updateMileageHist(previousReview);
        log.info("prehist" + preMileageHist.toString());

        //마일리지 적용 기록 및 차 획득
        Integer mileageDiff = mileageHistService.insertMileageHist(preMileageHist, result, mileage);

        //사용자 계좌 마일리지 적용
        UserAcct userAcct = userAcctService.updateUserAcct(model, mileageDiff);
        log.info("UPDATE : " + userAcct.toString());

        return new ResponseEntity(HttpStatus.CREATED);
    }

    public ResponseEntity delete(RequestReviewModel model) {
        //이전 리뷰 획득 및 비 활성화
        Review previousReview = reviewService.updateReviewUseFlag(model);
        log.info("UPDATE : " + previousReview.toString());

        //이전 마일리지 획득 비 활성화 및 획득
        MileageHist preMileageHist = mileageHistService.updateMileageHist(previousReview);
        log.info("prehist" + preMileageHist.toString());

        Integer mileage = 0;
        mileage = mileage - preMileageHist.getMileage();

        //사용자 계좌 마일리지 적용
        UserAcct userAcct = userAcctService.updateUserAcct(model, mileage);
        log.info("UPDATE : " + userAcct.toString());

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
