package com.example.simplework.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum UserPrivilegeConstant implements BaseEnum {
    GUEST(1), CUSTOMER(2), ADMIN(3);

    private static final Map<Integer, UserPrivilegeConstant> mapping = new HashMap<>();

    static {
        for (UserPrivilegeConstant upc : UserPrivilegeConstant.values()) {
            mapping.put(upc.value, upc);
        }
    }

    private final int value;

    UserPrivilegeConstant(int value) {
        this.value = value;
    }

    @JsonCreator
    public static UserPrivilegeConstant fromValue(int value) {
        if (mapping.containsKey(value)) {
            return mapping.get(value);
        }
        return null;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public UserPrivilegeConstant map(int value) {
        return fromValue(value);
    }
}
