package com.skyproton.kyc_backend.mapper;

import com.skyproton.kyc_backend.dto.userInfo.ReqUserInfoDTO;
import com.skyproton.kyc_backend.dto.userInfo.ResAddUserInfoDTO;
import com.skyproton.kyc_backend.dto.userInfo.ResGetUserInfoDTO;
import com.skyproton.kyc_backend.entity.UserInfo;
import com.skyproton.kyc_backend.tools.Crypto;
import com.skyproton.kyc_backend.tools.Generator;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class UserInfoMapper {

    private final Crypto crypto;
    private final Generator generator;

    public UserInfoMapper(Crypto crypto, Generator generator) {
        this.crypto = crypto;
        this.generator = generator;
    }

    public UserInfo mapToUserInfo(ReqUserInfoDTO reqUserInfoDTO) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUuid(generator.generateUuid());
        userInfo.setEmail(crypto.encrypt(reqUserInfoDTO.getEmail().toUpperCase(Locale.ROOT)));
        userInfo.setFirst_name(crypto.encrypt(reqUserInfoDTO.getFirst_name().toUpperCase(Locale.ROOT)));
        userInfo.setLast_name(crypto.encrypt(reqUserInfoDTO.getLast_name().toUpperCase(Locale.ROOT)));
        userInfo.setPhone_number(crypto.encrypt(reqUserInfoDTO.getPhone_number()));
        userInfo.setNationality(crypto.encrypt(reqUserInfoDTO.getNationality().toUpperCase(Locale.ROOT)));
        userInfo.setIs_verified(false);

        return userInfo;
    }

    public ResAddUserInfoDTO mapToResAddUserInfoDTO(UserInfo userInfo, String accountUuid, String accountUsername) {
        ResAddUserInfoDTO res = new ResAddUserInfoDTO();
        res.setUser_info_uuid(userInfo.getUuid());
        res.setMessage("We are processing your personal information. Please wait for verification.");
        res.setAccount_username(accountUsername);
        res.setAccount_uuid(accountUuid);
        res.setAdded_successfully(true);

        return res;
    }

    public ResGetUserInfoDTO mapToResGetUserInfoDTO(UserInfo userInfo, String accountUuid) {
        ResGetUserInfoDTO res = new ResGetUserInfoDTO();
        res.setAccount_uuid(accountUuid);
        res.setUser_info_uuid(userInfo.getUuid());
        res.setFirst_name(crypto.decrypt(userInfo.getFirst_name()));
        res.setLast_name(crypto.decrypt(userInfo.getLast_name()));
        res.setEmail(crypto.decrypt(userInfo.getEmail()));
        res.setPhone_number(crypto.decrypt(userInfo.getPhone_number()));
        res.setNationality(crypto.decrypt(userInfo.getNationality()));
        res.setIs_verified(userInfo.getIs_verified());

        return res;
    }
}
