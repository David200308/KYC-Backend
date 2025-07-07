package com.skyproton.kyc_backend.dto.userInfo;

import lombok.Data;

@Data
public class ResAddUserInfoDTO {
    private String account_uuid;
    private String account_username;
    private String user_info_uuid;
    private Boolean added_successfully;
    private String message;
}
