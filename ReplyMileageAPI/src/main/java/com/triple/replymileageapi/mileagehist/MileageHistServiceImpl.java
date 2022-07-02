package com.triple.replymileageapi.mileagehist;

import com.triple.replymileageapi.mileage.MileageService;
import com.triple.replymileageapi.review.Review;
import com.triple.replymileageapi.review.ReviewRepo;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

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
                .useFlag("Y")
                .bonusFlag(placeReViewCount == 0 ? "Y" : "N")
                .build();

        mileageHistRepo.save(mileageHist);

        return mileageHistRepo.findByReviewId(mileageHist.getReviewId()).get(0);
    }

    @Override
    public Integer insertMileageHist(MileageHist preMileageHist, Review review, Integer mileage) {
        Integer claculatedMileage = preMileageHist.getMileage() + mileage;

                MileageHist mileageHist = MileageHist.builder()
                .userId(review.getUserId())
                .placeId(review.getPlaceId())
                .reviewId(review.getReviewId())
                .mileage(claculatedMileage)
                .useFlag("Y")
                .bonusFlag(preMileageHist.getBonusFlag())
                .build();

        mileageHistRepo.save(mileageHist);

        return claculatedMileage - preMileageHist.getMileage();
    }

    @Override
    public MileageHist updateMileageHist(Review review) {
        MileageHist previousHist = mileageHistRepo.findByReviewIdAndPlaceIdAndUseFlag(review.getReviewId(), review.getPlaceId(), "Y");

        previousHist.setUseFlag("N");

        mileageHistRepo.save(previousHist);

        return mileageHistRepo.findByReviewIdAndPlaceIdAndUseFlagOrderByCreatedDesc(review.getReviewId(), review.getPlaceId(), "N").get(0);
    }
}
