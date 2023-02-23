package com.greentea.surgom.vo;

import lombok.Data;

@Data
public class NaverProfileResponseVo {
    private String resultcode;
    private String message;
    private NaverProfileVo response;
}
