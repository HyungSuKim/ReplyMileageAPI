package com.triple.replymileageapi.mileagehist;

import com.triple.replymileageapi.review.Review;
import com.triple.replymileageapi.useracct.UserAcct;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;

@Log
@Service
public class MileageHistServiceImpl implements MileageHistService{
    private final MileageHistRepo mileageHistRepo;

    MileageHistServiceImpl(MileageHistRepo mileageHistRepo) {
        this.mileageHistRepo = mileageHistRepo;
    }

    @Override
    public MileageHist insertMileageHist(Review review, Integer mileage, Integer placeReViewCount, UserAcct userAcct) {
        MileageHist mileageHist = MileageHist.builder()
                .userAcct(userAcct)
                .placeId(review.getPlaceId())
                .review(review)
                .mileage(mileage)
                .mileageChange(mileage)
                .action("ADD")
                .useFlag("Y")
                .bonusFlag(placeReViewCount == 0 ? "Y" : "N")
                .build();

        mileageHistRepo.save(mileageHist);

        return mileageHistRepo.findByReview(mileageHist.getReview()).get(0);
    }

    @Override
    public Integer insertMileageHist(MileageHist preMileageHist, Review review, Integer mileage) {
        Integer calculatedMileage = preMileageHist.getMileage() + mileage;

        MileageHist mileageHist = MileageHist.builder()
                .userAcct(preMileageHist.getUserAcct())
                .placeId(review.getPlaceId())
                .review(review)
                .mileage(calculatedMileage)
                .mileageChange(mileage)
                .action("MOD")
                .useFlag("Y")
                .bonusFlag(preMileageHist.getBonusFlag())
                .build();

        mileageHistRepo.save(mileageHist);

        return calculatedMileage - preMileageHist.getMileage();
    }

    @Override
    public void insertMileageHist(MileageHist preMileageHist, Integer mileage) {
        MileageHist mileageHist = MileageHist.builder()
                .userAcct(preMileageHist.getUserAcct())
                .placeId(preMileageHist.getPlaceId())
                .review(preMileageHist.getReview())
                .mileage(preMileageHist.getMileage())
                .mileageChange(mileage)
                .action("DELETE")
                .useFlag("N")
                .bonusFlag(preMileageHist.getBonusFlag())
                .build();

        mileageHistRepo.save(mileageHist);
    }

    @Override
    public MileageHist updateMileageHist(Review review) {
        MileageHist previousHist = mileageHistRepo.findByReviewAndPlaceIdAndUseFlag(review, review.getPlaceId(), "Y");

        previousHist.setUseFlag("N");

        mileageHistRepo.save(previousHist);

        return mileageHistRepo.findByReviewAndPlaceIdAndUseFlagOrderByCreatedDesc(review, review.getPlaceId(), "N").get(0);
    }

    @Override
    public List<MileageHist> getMileageHist(String userId) {
        log.info("get 호출");
        return mileageHistRepo.findByUserAcctOrderByCreatedDesc(UserAcct.builder().userId(userId).build());
    }

    @Override
    public List<MileageHist> getMileageHist(String userId, String placeId) {
        return mileageHistRepo.findByUserAcctAndPlaceIdOrderByCreatedDesc(UserAcct.builder().userId(userId).build(), placeId);
    }

    @Override
    public List<MileageHist> getMileageHist(String userId, String placeId, String reviewId) {
        return mileageHistRepo.findByUserAcctAndPlaceIdAndReviewOrderByCreatedDesc(
                UserAcct.builder().userId(userId).build(), placeId, Review.builder().reviewId(reviewId).build());
    }
}
