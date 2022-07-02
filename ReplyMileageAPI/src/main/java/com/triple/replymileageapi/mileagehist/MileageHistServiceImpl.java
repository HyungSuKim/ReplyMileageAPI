package com.triple.replymileageapi.mileagehist;

import com.triple.replymileageapi.review.Review;
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
    public MileageHist insertMileageHist(Review review, Integer mileage, Integer placeReViewCount) {
        MileageHist mileageHist = MileageHist.builder()
                .userId(review.getUserId())
                .placeId(review.getPlaceId())
                .reviewId(review.getReviewId())
                .mileage(mileage)
                .mileageChange(mileage)
                .action("ADD")
                .useFlag("Y")
                .bonusFlag(placeReViewCount == 0 ? "Y" : "N")
                .build();

        mileageHistRepo.save(mileageHist);

        return mileageHistRepo.findByReviewId(mileageHist.getReviewId()).get(0);
    }

    @Override
    public Integer insertMileageHist(MileageHist preMileageHist, Review review, Integer mileage) {
        Integer calculatedMileage = preMileageHist.getMileage() + mileage;

        MileageHist mileageHist = MileageHist.builder()
                .userId(review.getUserId())
                .placeId(review.getPlaceId())
                .reviewId(review.getReviewId())
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
                .userId(preMileageHist.getUserId())
                .placeId(preMileageHist.getPlaceId())
                .reviewId(preMileageHist.getReviewId())
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
        MileageHist previousHist = mileageHistRepo.findByReviewIdAndPlaceIdAndUseFlag(review.getReviewId(), review.getPlaceId(), "Y");

        previousHist.setUseFlag("N");

        mileageHistRepo.save(previousHist);

        return mileageHistRepo.findByReviewIdAndPlaceIdAndUseFlagOrderByCreatedDesc(review.getReviewId(), review.getPlaceId(), "N").get(0);
    }

    @Override
    public List<MileageHist> getMileageHist(String userId) {
        return mileageHistRepo.findByUserIdOrderByCreatedDesc(userId);
    }

    @Override
    public List<MileageHist> getMileageHist(String userId, String placeId) {
        return mileageHistRepo.findByUserIdAndPlaceIdOrderByCreatedDesc(userId, placeId);
    }

    @Override
    public List<MileageHist> getMileageHist(String userId, String placeId, String reviewId) {
        return mileageHistRepo.findByUserIdAndPlaceIdAndReviewIdOrderByCreatedDesc(userId, placeId, reviewId);
    }
}
