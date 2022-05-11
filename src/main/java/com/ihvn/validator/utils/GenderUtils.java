package com.ihvn.validator.utils;

public enum GenderUtils {
    FEMALE("F"), MALE("M");

    private String type;

    GenderUtils(String type) {
        this.type = type;
    }

    public  String getType() {
        return type;
    }
}
