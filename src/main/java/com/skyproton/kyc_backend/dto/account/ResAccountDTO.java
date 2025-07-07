package com.skyproton.kyc_backend.dto.account;

import com.skyproton.kyc_backend.variable.AccountStatus;
import lombok.Data;

@Data
public class ResAccountDTO {
    private Long id;
    private String uuid;
    private String user_info_uuid;
    private String username;
    private AccountStatus status;
}
