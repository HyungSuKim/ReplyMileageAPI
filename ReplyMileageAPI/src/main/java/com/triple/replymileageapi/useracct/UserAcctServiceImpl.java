package com.triple.replymileageapi.useracct;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserAcctServiceImpl implements UserAcctService{

    private final UserAcctRepo userAcctRepo;

    public UserAcctServiceImpl(UserAcctRepo userAcctRepo) {
        this.userAcctRepo = userAcctRepo;
    }

    @Override
    public UserAcct makeUserAcct(UserAcct userAcct) {

        List<UserAcct> searchResult = userAcctRepo.findByUserId(userAcct.getUserId());

        if(!searchResult.isEmpty()) {
            return searchResult.get(0);
        }

        UserAcct result = userAcctRepo.save(userAcct);

        return result;
    }
}
