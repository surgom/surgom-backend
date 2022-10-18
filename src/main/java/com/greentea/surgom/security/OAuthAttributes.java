package com.greentea.surgom.security;

import com.greentea.surgom.domain.Authority;
import com.greentea.surgom.domain.Gender;
import com.greentea.surgom.domain.Member;
import com.greentea.surgom.domain.Token;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String nickname;
    private String gender;
    private String birthyear;
    private String mobile;
    private String access_token;
    private String refresh_token;

    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String nickname, String gender, String birthyear, String mobile, String access_token, String refresh_token) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.birthyear = birthyear;
        this.mobile = mobile;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }

    public OAuthAttributes() {
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if (registrationId.equals("naver"))
            return ofNaver(userNameAttributeName, attributes);
        else
            return null;
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return new OAuthAttributes(attributes,
                userNameAttributeName,
                (String) response.get("name"),
                (String) response.get("nickname"),
                (String) response.get("gender"),
                (String) response.get("birthyear"),
                (String) response.get("mobile"),
                (String) response.get("access_token"),
                (String) response.get("refresh_token"));
    }

    public Member toMemberEntity(String registrationId) {
        Gender enumGender;
        if (gender.equals("F")) enumGender = Gender.FEMALE;
        else enumGender = Gender.MALE;

        return new Member(mobile, nickname, name, Integer.parseInt(birthyear), enumGender, 0L, Authority.USER, registrationId);
    }

    public Token toTokenEntity() {
        return new Token(mobile, access_token, refresh_token, null, null);
    }
}