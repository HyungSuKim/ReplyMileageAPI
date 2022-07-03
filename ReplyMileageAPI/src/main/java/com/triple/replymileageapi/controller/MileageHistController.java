package com.triple.replymileageapi.controller;

import com.triple.replymileageapi.controller.model.ResponseHistModel;
import com.triple.replymileageapi.mileagehist.MileageHist;
import com.triple.replymileageapi.mileagehist.MileageHistService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Log
@RestController
@RequestMapping("history")
public class MileageHistController {
    private final MileageHistService mileageHistService;

    public MileageHistController(MileageHistService mileageHistService) {
        this.mileageHistService = mileageHistService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<ResponseHistModel>> getUserMileageHist(@RequestParam String userId) {
        List<MileageHist> mileageHistList = mileageHistService.getMileageHist(userId);
        List<ResponseHistModel> responseHistModelList = new ArrayList<>();

        if(mileageHistList.size() == 0)
            return new ResponseEntity<>(responseHistModelList, HttpStatus.NOT_FOUND);

        for (MileageHist mileageHist : mileageHistList) {
            responseHistModelList.add(ResponseHistModel.builder()
                            .id(mileageHist.getId())
                            .userId(mileageHist.getUserAcct().getUserId())
                            .placeId(mileageHist.getPlaceId())
                            .reviewId(mileageHist.getReview().getReviewId())
                            .mileage(mileageHist.getMileage())
                            .mileageChange(mileageHist.getMileageChange())
                            .action(mileageHist.getAction())
                    .build());
        }

        return new ResponseEntity<>(responseHistModelList, HttpStatus.OK);
    }

    @GetMapping("/user/place")
    public ResponseEntity<List<ResponseHistModel>> getUserMileageHist(@RequestParam String userId, @RequestParam String placeId) {
        List<MileageHist> mileageHistList = mileageHistService.getMileageHist(userId, placeId);

        List<ResponseHistModel> responseHistModelList = new ArrayList<>();

        if(mileageHistList.size() == 0)
            return new ResponseEntity<>(responseHistModelList, HttpStatus.NOT_FOUND);

        for (MileageHist mileageHist : mileageHistList) {
            responseHistModelList.add(ResponseHistModel.builder()
                    .id(mileageHist.getId())
                    .userId(mileageHist.getUserAcct().getUserId())
                    .placeId(mileageHist.getPlaceId())
                    .reviewId(mileageHist.getReview().getReviewId())
                    .mileage(mileageHist.getMileage())
                    .mileageChange(mileageHist.getMileageChange())
                    .action(mileageHist.getAction())
                    .build());
        }

        return new ResponseEntity<>(responseHistModelList, HttpStatus.OK);
    }

    @GetMapping("/user/place/review")
    public ResponseEntity<List<ResponseHistModel>> getUserMileageHist(@RequestParam String userId, @RequestParam String placeId, @RequestParam String reviewId) {
        List<MileageHist> mileageHistList = mileageHistService.getMileageHist(userId, placeId, reviewId);
        List<ResponseHistModel> responseHistModelList = new ArrayList<>();

        if(mileageHistList.size() == 0)
            return new ResponseEntity<>(responseHistModelList, HttpStatus.NOT_FOUND);

        for (MileageHist mileageHist : mileageHistList) {
            responseHistModelList.add(ResponseHistModel.builder()
                    .id(mileageHist.getId())
                    .userId(mileageHist.getUserAcct().getUserId())
                    .placeId(mileageHist.getPlaceId())
                    .reviewId(mileageHist.getReview().getReviewId())
                    .mileage(mileageHist.getMileage())
                    .mileageChange(mileageHist.getMileageChange())
                    .action(mileageHist.getAction())
                    .build());
        }

        return new ResponseEntity<>(responseHistModelList, HttpStatus.OK);
    }
}
