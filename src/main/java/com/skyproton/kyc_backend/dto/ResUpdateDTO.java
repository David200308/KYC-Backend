package com.skyproton.kyc_backend.dto;

import lombok.Data;

@Data
public class ResUpdateDTO {
    private String uuid;
    private String field;
    private Boolean is_success;
    private String message;
}
