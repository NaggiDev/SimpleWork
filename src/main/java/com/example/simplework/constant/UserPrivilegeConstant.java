package com.example.simplework.constant;

public enum UserPrivilegeConstant {
    GUEST(1), CUSTOMER(2), ADMIN(3);
    private final int status;

    UserPrivilegeConstant(int status) {
        this.status = status;
    }


}
