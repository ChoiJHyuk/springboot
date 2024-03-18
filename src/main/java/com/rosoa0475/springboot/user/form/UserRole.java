package com.rosoa0475.springboot.user.form;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String code){
        this.code = code;
    }

    private final String code;
}
