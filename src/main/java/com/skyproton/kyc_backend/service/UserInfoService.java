package com.skyproton.kyc_backend.service;

import com.skyproton.kyc_backend.dto.ResUpdateDTO;
import com.skyproton.kyc_backend.dto.userInfo.ReqUserInfoDTO;
import com.skyproton.kyc_backend.dto.userInfo.ResAddUserInfoDTO;
import com.skyproton.kyc_backend.dto.userInfo.ResGetUserInfoDTO;

public interface UserInfoService {
    ResAddUserInfoDTO addUserInfo(ReqUserInfoDTO reqUserInfoDTO, String accountUuid);
    ResGetUserInfoDTO getUserInfoByUuid(String accountUuid);
    ResUpdateDTO updateUserInfoIsVerified(String uuid, Boolean isVerified);
}
