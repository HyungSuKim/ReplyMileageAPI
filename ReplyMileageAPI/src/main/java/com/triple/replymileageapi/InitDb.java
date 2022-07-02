package com.triple.replymileageapi;

import com.triple.replymileageapi.useracct.UserAcct;
import com.triple.replymileageapi.useracct.UserAcctRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInitUserAcct();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    @Log
    static class InitService {
        @Autowired
        private UserAcctRepo userAcctRepo;

        public void dbInitUserAcct() {
            for(int i = 0; i < 5; i++) {
                UserAcct userAcct = UserAcct.builder().userId(UUID.randomUUID().toString()).mileage(0).build();
                userAcctRepo.save(userAcct);
            }

            log.info("" + userAcctRepo.findAll().size());
        }
    }
}
