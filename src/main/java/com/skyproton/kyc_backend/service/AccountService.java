package com.skyproton.kyc_backend.service;

import com.skyproton.kyc_backend.dto.account.ReqAccountDTO;
import com.skyproton.kyc_backend.dto.account.ResAccountDTO;
import com.skyproton.kyc_backend.dto.ResUpdateDTO;
import com.skyproton.kyc_backend.variable.AccountStatus;

public interface AccountService {
    ResAccountDTO createAccount(ReqAccountDTO reqAccountDTO);
    ResAccountDTO getAccountByUuid(String uuid);
    ResUpdateDTO updateAccountStatus(String uuid, AccountStatus status);
}
