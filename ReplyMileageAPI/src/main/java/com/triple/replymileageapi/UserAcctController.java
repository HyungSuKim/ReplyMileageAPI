package com.triple.replymileageapi;

import com.triple.replymileageapi.useracct.UserAcct;
import com.triple.replymileageapi.useracct.UserAcctService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
public class UserAcctController {
    private final UserAcctService userAcctService;

    public UserAcctController(UserAcctService userAcctService) {
        this.userAcctService = userAcctService;
    }

    @GetMapping("/useracct")
    public ResponseEntity<UserAcct> getUserAcct(@RequestParam String userId) {
        UserAcct userAcct = userAcctService.getUserAcct(userId);

        return new ResponseEntity<>(userAcct, HttpStatus.OK);
    }
}
