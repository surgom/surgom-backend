package com.greentea.surgom.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {
    private String jwt_access_token;
    private String jwt_refresh_token;
}
