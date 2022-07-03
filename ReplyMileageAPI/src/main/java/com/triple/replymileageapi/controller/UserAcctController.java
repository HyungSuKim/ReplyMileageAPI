package com.triple.replymileageapi.controller;

import com.triple.replymileageapi.controller.model.ResponseHistModel;
import com.triple.replymileageapi.controller.model.ResponseUserAcctModel;
import com.triple.replymileageapi.mileagehist.MileageHist;
import com.triple.replymileageapi.mileagehist.MileageHistService;
import com.triple.replymileageapi.useracct.UserAcct;
import com.triple.replymileageapi.useracct.UserAcctService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Log
@RestController
public class UserAcctController {
    private final UserAcctService userAcctService;
    private final MileageHistService mileageHistService;

    public UserAcctController(UserAcctService userAcctService, MileageHistService mileageHistService) {
        this.userAcctService = userAcctService;
        this.mileageHistService = mileageHistService;
    }

    @GetMapping("/useracct")
    public ResponseEntity<ResponseUserAcctModel> getUserAcct(@RequestParam String userId) {
        UserAcct userAcct = userAcctService.getUserAcct(userId);
        List<MileageHist> mileageHistList = mileageHistService.getMileageHist(userId);
        List<ResponseHistModel> responseHistModelList = new ArrayList<>();

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

        return new ResponseEntity<>(ResponseUserAcctModel.builder()
                .userId(userAcct.getUserId())
                .mileage(userAcct.getMileage())
                .history(responseHistModelList)
                .build(), HttpStatus.OK);
    }
}
