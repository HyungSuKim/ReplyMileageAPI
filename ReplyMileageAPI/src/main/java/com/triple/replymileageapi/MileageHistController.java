package com.triple.replymileageapi;

import com.triple.replymileageapi.mileagehist.MileageHist;
import com.triple.replymileageapi.mileagehist.MileageHistService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<MileageHist>> getUserMileageHist(@RequestParam String userId) {
        List<MileageHist> mileageHistList = mileageHistService.getMileageHist(userId);

        if(mileageHistList.size() == 0)
            return new ResponseEntity<>(mileageHistList, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(mileageHistList, HttpStatus.OK);
    }

    @GetMapping("/user/place")
    public ResponseEntity<List<MileageHist>> getUserMileageHist(@RequestParam String userId, @RequestParam String placeId) {
        List<MileageHist> mileageHistList = mileageHistService.getMileageHist(userId, placeId);

        if(mileageHistList.size() == 0)
            return new ResponseEntity<>(mileageHistList, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(mileageHistList, HttpStatus.OK);
    }

    @GetMapping("/user/place/review")
    public ResponseEntity<List<MileageHist>> getUserMileageHist(@RequestParam String userId, @RequestParam String placeId, @RequestParam String reviewId) {
        List<MileageHist> mileageHistList = mileageHistService.getMileageHist(userId, placeId, reviewId);

        if(mileageHistList.size() == 0)
            return new ResponseEntity<>(mileageHistList, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(mileageHistList, HttpStatus.OK);
    }
}
