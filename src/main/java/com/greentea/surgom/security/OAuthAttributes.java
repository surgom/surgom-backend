package com.greentea.surgom.security;

import com.greentea.surgom.domain.Authority;
import com.greentea.surgom.domain.Gender;
import com.greentea.surgom.domain.Member;
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

    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String nickname, String gender, String birthyear, String mobile) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.birthyear = birthyear;
        this.mobile = mobile;
    }

    public OAuthAttributes() {
    }

    // 해당 로그인인 서비스가 kakao인지 google인지 구분하여, 알맞게 매핑을 해주도록 합니다.
    // 여기서 registrationId는 OAuth2 로그인을 처리한 서비스 명("google","kakao","naver"..)이 되고,
    // userNameAttributeName은 해당 서비스의 map의 키값이 되는 값이됩니다. {google="sub", kakao="id", naver="response"}
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if (registrationId.equals("naver"))
            return ofNaver(userNameAttributeName, attributes);
        else
            return null;
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");    // 네이버에서 받은 데이터에서 프로필 정보다 담긴 response 값을 꺼낸다.

        return new OAuthAttributes(attributes,
                userNameAttributeName,
                (String) response.get("name"),
                (String) response.get("nickname"),
                (String) response.get("gender"),
                (String) response.get("birthyear"),
                (String) response.get("mobile"));
    }

    // oauth2Service에서 registrationId까지 받아와 provider의 이름까지 저장해줍니다.
    public Member toEntity(String registrationId) {
        Gender enumGender;
        if (gender.equals("F")) enumGender = Gender.FEMALE;
        else enumGender = Gender.MALE;

        return new Member(mobile, nickname, name, Integer.parseInt(birthyear), enumGender, 0L, Authority.USER, nameAttributeKey);
    }
}