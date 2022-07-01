package com.triple.replymileageapi.useracct;

import java.util.List;
import java.util.UUID;

public class UserAcctServiceImpl implements UserAcctService{

    private final UserAcctRepo userAcctRepo;

    UserAcctServiceImpl(UserAcctRepo userAcctRepo) {
        this.userAcctRepo = userAcctRepo;
    }

    @Override
    public UUID makeUserAcct(UserAcct userAcct) {

        List<UserAcct> searchResult = userAcctRepo.findByUserId(userAcct.getUserId());

        if(!searchResult.isEmpty()) {
            return userAcct.getUserId();
        }

        UserAcct result = userAcctRepo.save(userAcct);

        return result.getUserId();
    }
}
