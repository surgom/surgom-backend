package com.greentea.surgom.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverProfile {
    private String resultcode;
    private String message;
    private Response response;

    @Getter
    @Setter
    public class Response {
        private String nickname;
        private String name;
        private String gender;
        private String birthyear;
        private String mobile;
    }
}