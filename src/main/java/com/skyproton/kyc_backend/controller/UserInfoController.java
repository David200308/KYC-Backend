package com.skyproton.kyc_backend.controller;

import com.skyproton.kyc_backend.dto.ResUpdateDTO;
import com.skyproton.kyc_backend.dto.userInfo.ReqUpdateUserInfoStatusDTO;
import com.skyproton.kyc_backend.dto.userInfo.ReqUserInfoDTO;
import com.skyproton.kyc_backend.dto.userInfo.ResAddUserInfoDTO;
import com.skyproton.kyc_backend.dto.userInfo.ResGetUserInfoDTO;
import com.skyproton.kyc_backend.service.UserInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/userinfo")
public class UserInfoController {
    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/{accountUuid}")
    public ResponseEntity<ResAddUserInfoDTO> addUserInfo(@RequestBody ReqUserInfoDTO reqUserInfoDTO, @PathVariable String accountUuid) {
        ResAddUserInfoDTO res = userInfoService.addUserInfo(reqUserInfoDTO, accountUuid);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/{accountUuid}")
    public ResponseEntity<ResGetUserInfoDTO> getUserInfo(@PathVariable String accountUuid) {
        try {
            ResGetUserInfoDTO userInfo = userInfoService.getUserInfoByUuid(accountUuid);
            return ResponseEntity.ok(userInfo);
        } catch (RuntimeException e) {
            switch (e.getMessage()) {
                case "ACCOUNT_NOT_FOUND":
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                default:
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @PatchMapping("/{accountUuid}/verify")
    public ResponseEntity<ResUpdateDTO> updateUserInfoIsVerified(@PathVariable String accountUuid, @RequestBody ReqUpdateUserInfoStatusDTO data) {
        ResUpdateDTO res = userInfoService.updateUserInfoIsVerified(accountUuid, data.getIs_verified());
        return ResponseEntity.ok(res);
    }

}
