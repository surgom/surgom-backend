package com.greentea.surgom.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    FEMALE("ROLE_FEMALE", "여자"),
    MALE("ROLE_MALE", "남자");

    private final String key;
    private final String title;
}
