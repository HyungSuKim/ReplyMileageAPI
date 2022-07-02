package com.triple.replymileageapi.useracct;

import com.triple.replymileageapi.RequestReviewModel;

import java.util.UUID;

public interface UserAcctService {
    UserAcct makeUserAcct(RequestReviewModel model);
    UserAcct updateUserAcct(RequestReviewModel model, Integer mileage);
}
