package com.triple.replymileageapi.useracct;

import com.triple.replymileageapi.RequestReviewModel;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log
@Service
public class UserAcctServiceImpl implements UserAcctService{

    private final UserAcctRepo userAcctRepo;

    public UserAcctServiceImpl(UserAcctRepo userAcctRepo) {
        this.userAcctRepo = userAcctRepo;
    }

    @Override
    public UserAcct makeUserAcct(RequestReviewModel model) {

        List<UserAcct> searchResult = userAcctRepo.findByUserId(model.getUserId());

        if(!searchResult.isEmpty()) {
            log.info(searchResult.get(0).toString());

            return searchResult.get(0);
        }

        UserAcct userAcct = UserAcct.builder()
                .userId(model.getUserId())
                .mileage(0)
                .build();

        userAcctRepo.save(userAcct);

        log.info(userAcctRepo.findByUserId(model.getUserId()).get(0).toString());

        return userAcctRepo.findByUserId(model.getUserId()).get(0);
    }

    @Override
    public UserAcct updateUserAcct(RequestReviewModel model, Integer milage) {
        UserAcct userAcct = userAcctRepo.findByUserId(model.getUserId()).get(0);

        userAcct.setMileage(userAcct.getMileage() + milage);

        userAcctRepo.save(userAcct);

        return userAcctRepo.findByUserId(model.getUserId()).get(0);
    }
}
