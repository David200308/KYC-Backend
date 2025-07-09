package com.skyproton.kyc_backend.service.impl;

import com.skyproton.kyc_backend.dto.account.ReqAccountDTO;
import com.skyproton.kyc_backend.dto.account.ResAccountDTO;
import com.skyproton.kyc_backend.dto.ResUpdateDTO;
import com.skyproton.kyc_backend.entity.Account;
import com.skyproton.kyc_backend.mapper.AccountMapper;
import com.skyproton.kyc_backend.mapper.Mapper;
import com.skyproton.kyc_backend.repository.AccountRepo;
import com.skyproton.kyc_backend.service.AccountService;
import com.skyproton.kyc_backend.variable.AccountStatus;
import com.skyproton.kyc_backend.variable.UpdateField;
import org.springframework.stereotype.Service;

import static com.skyproton.kyc_backend.error.Error.ACCOUNT_NOT_FOUND;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final AccountMapper accountMapper;
    private final Mapper mapper;

    public AccountServiceImpl(AccountRepo accountRepo, AccountMapper accountMapper, Mapper mapper) {
        this.accountRepo = accountRepo;
        this.accountMapper = accountMapper;
        this.mapper = mapper;
    }

    @Override
    public ResAccountDTO createAccount(ReqAccountDTO reqAccountDTO) {
        Account account = accountMapper.mapToAccount(reqAccountDTO);
        Account savedAccount = accountRepo.save(account);
        return accountMapper.mapToResAccountDTO(savedAccount);
    }

    @Override
    public ResAccountDTO getAccountByUuid(String uuid) {
        try {
            Account account = accountRepo.findByUuid(uuid);
            return accountMapper.mapToResAccountDTO(account);
        } catch (Exception e) {
            throw new RuntimeException(ACCOUNT_NOT_FOUND);
        }
    }

    @Override
    public ResUpdateDTO updateAccountStatus(String uuid, AccountStatus status) {
        try {
            if (accountRepo.findByUuid(uuid) == null) throw new RuntimeException("Account not found with UUID: " + uuid);
            if (status == AccountStatus.UNVERIFIED) throw new RuntimeException("No permission! User cannot modify status to UNVERIFIED");

            return mapper.mapToResUpdateDTO(
                    uuid,
                    UpdateField.ACCOUNT_STATUS,
                    true,
                    "Account status updated successfully"
            );
        } catch (Exception e) {
            return mapper.mapToResUpdateDTO(
                    uuid,
                    UpdateField.ACCOUNT_STATUS,
                    false,
                    "Failed to update account status: " + e.getMessage()
            );
        }
    }

}
