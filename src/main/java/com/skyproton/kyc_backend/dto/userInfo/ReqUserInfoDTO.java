package com.skyproton.kyc_backend.dto.userInfo;

import lombok.Data;

@Data
public class ReqUserInfoDTO {
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private String nationality;
}
