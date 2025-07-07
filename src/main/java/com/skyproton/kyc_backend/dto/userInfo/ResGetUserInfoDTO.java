package com.skyproton.kyc_backend.dto.userInfo;

import lombok.Data;

@Data
public class ResGetUserInfoDTO {
    private String account_uuid;
    private String user_info_uuid;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private String nationality;
    private Boolean is_verified;
}
