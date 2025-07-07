package com.skyproton.kyc_backend.variable;

public enum AccountStatus {
    // "active", "unverified", "suspended", "blocked", "deleted"
    ACTIVE("ACTIVE"),
    UNVERIFIED("UNVERIFIED"),
    SUSPENDED("SUSPENDED"),
    BLOCKED("BLOCKED"),
    DELETED("DELETED");

    private final String status;

    AccountStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
