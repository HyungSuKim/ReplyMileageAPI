package com.triple.replymileageapi.controller.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ResponseUserAcctModel {

    private String userId;

    private Integer mileage;

    private List<ResponseHistModel> history;
}
