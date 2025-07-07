package com.skyproton.kyc_backend.dto.account;

import com.skyproton.kyc_backend.variable.AccountStatus;
import lombok.Data;

@Data
public class ReqUpdateAccountStatusDTO {
    private AccountStatus status;
}
