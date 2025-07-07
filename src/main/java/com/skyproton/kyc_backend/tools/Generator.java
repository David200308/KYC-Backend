package com.skyproton.kyc_backend.tools;

import org.springframework.stereotype.Component;

@Component
public class Generator {
    public String generateUuid() {
        return java.util.UUID.randomUUID().toString();
    }
}
