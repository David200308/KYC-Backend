package com.skyproton.kyc_backend.mapper;

import com.skyproton.kyc_backend.dto.account.ReqAccountDTO;
import com.skyproton.kyc_backend.dto.account.ResAccountDTO;
import com.skyproton.kyc_backend.entity.Account;
import com.skyproton.kyc_backend.tools.Crypto;
import com.skyproton.kyc_backend.tools.Generator;
import com.skyproton.kyc_backend.variable.AccountStatus;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    private final Crypto crypto;
    private final Generator generator;

    public AccountMapper(Crypto crypto, Generator generator) {
        this.crypto = crypto;
        this.generator = generator;
    }

    public Account mapToAccount(ReqAccountDTO reqAccountDTO) {
        Account account = new Account();
        account.setUuid(generator.generateUuid());
        account.setUsername(reqAccountDTO.getUsername());
        account.setPassword(crypto.hashPassword(reqAccountDTO.getPassword()));
        account.setStatus(AccountStatus.UNVERIFIED.name());

        return account;
    }

    public ResAccountDTO mapToResAccountDTO(Account account) {
        if (account == null) {
            return null;
        }
        ResAccountDTO resAccountDTO = new ResAccountDTO();
        resAccountDTO.setId(account.getId());
        resAccountDTO.setUuid(account.getUuid());
        resAccountDTO.setUser_info_uuid(account.getUser_info_uuid() != null ? account.getUser_info_uuid() : "");
        resAccountDTO.setUsername(account.getUsername());
        resAccountDTO.setStatus(AccountStatus.valueOf(account.getStatus()));

        return resAccountDTO;
    }

}
