package com.skyproton.kyc_backend.variable;

public enum UpdateField {
    USERNAME("username"),
    PASSWORD("password"),
    EMAIL("email"),
    PHONE_NUMBER("phone number"),
    ACCOUNT_STATUS("account status"),
    USERINFO_VERIFY_STATUS("user information verify status"),
    ACCOUNT_AND_USERINFO_VERIFY_STATUS("account and user information verify status");

    private final String field;

    UpdateField(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
