package com.skyproton.kyc_backend.service.impl;

import com.skyproton.kyc_backend.dto.ResUpdateDTO;
import com.skyproton.kyc_backend.dto.userInfo.ReqUserInfoDTO;
import com.skyproton.kyc_backend.dto.userInfo.ResAddUserInfoDTO;
import com.skyproton.kyc_backend.dto.userInfo.ResGetUserInfoDTO;
import com.skyproton.kyc_backend.entity.Account;
import com.skyproton.kyc_backend.entity.UserInfo;
import com.skyproton.kyc_backend.mapper.Mapper;
import com.skyproton.kyc_backend.mapper.UserInfoMapper;
import com.skyproton.kyc_backend.repository.AccountRepo;
import com.skyproton.kyc_backend.repository.UserInfoRepo;
import com.skyproton.kyc_backend.service.UserInfoService;
import com.skyproton.kyc_backend.variable.AccountStatus;
import com.skyproton.kyc_backend.variable.UpdateField;
import org.springframework.stereotype.Service;

import static com.skyproton.kyc_backend.error.Error.ACCOUNT_NOT_FOUND;
import static com.skyproton.kyc_backend.error.Error.INTERNAL_SERVER_ERROR;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private final AccountRepo accountRepo;
    private final UserInfoRepo userInfoRepo;
    private final UserInfoMapper userInfoMapper;
    private final Mapper mapper;

    public UserInfoServiceImpl(AccountRepo accountRepo, UserInfoRepo userInfoRepo, UserInfoMapper userInfoMapper, Mapper mapper) {
        this.accountRepo = accountRepo;
        this.userInfoRepo = userInfoRepo;
        this.userInfoMapper = userInfoMapper;
        this.mapper = mapper;
    }

    @Override
    public ResAddUserInfoDTO addUserInfo(ReqUserInfoDTO reqUserInfoDTO, String accountUuid) {
        Account account = accountRepo.findByUuid(accountUuid);
        if (account == null) {
            throw new RuntimeException("Account not found with UUID: " + accountUuid);
        }

        UserInfo userInfo = userInfoMapper.mapToUserInfo(reqUserInfoDTO);
        UserInfo savedUserInfo = userInfoRepo.save(userInfo);

        accountRepo.addUserInfoUuidToAccount(accountUuid, savedUserInfo.getUuid());

        return userInfoMapper.mapToResAddUserInfoDTO(savedUserInfo, accountUuid, account.getUsername());
    }

    @Override
    public ResGetUserInfoDTO getUserInfoByUuid(String accountUuid) {
        try {
            Account account = accountRepo.findByUuid(accountUuid);
            if (account == null) {
                throw new RuntimeException(ACCOUNT_NOT_FOUND);
            }
            UserInfo userInfo = userInfoRepo.findByUuid(account.getUser_info_uuid());
            if (userInfo == null) {
                throw new RuntimeException("User Info not found for account UUID: " + accountUuid);
            }
            return userInfoMapper.mapToResGetUserInfoDTO(userInfo, accountUuid);
        } catch (Exception e) {
            throw new RuntimeException(INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResUpdateDTO updateUserInfoIsVerified(String uuid, Boolean isVerified) {
        try {
            Account account = accountRepo.findByUuid(uuid);
            if (account == null) throw new RuntimeException("Account not found with UUID: " + uuid);

            userInfoRepo.updateUserInfoVerifyStatus(account.getUser_info_uuid(), isVerified);
            accountRepo.updateAccountStatusByUuid(
                    uuid,
                    isVerified ?
                            AccountStatus.ACTIVE.getStatus() :
                            AccountStatus.UNVERIFIED.getStatus()
            );

            return mapper.mapToResUpdateDTO(
                    uuid,
                    UpdateField.ACCOUNT_AND_USERINFO_VERIFY_STATUS,
                    true,
                    "User Info & Account status updated successfully"
            );
        } catch (Exception e) {
            return mapper.mapToResUpdateDTO(
                    uuid,
                    UpdateField.ACCOUNT_AND_USERINFO_VERIFY_STATUS,
                    false,
                    "Failed to update User Info & Account status: " + e.getMessage()
            );
        }
    }
}
