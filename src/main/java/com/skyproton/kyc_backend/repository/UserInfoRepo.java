package com.skyproton.kyc_backend.repository;

import com.skyproton.kyc_backend.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {
    @Query("SELECT u FROM UserInfo u WHERE u.uuid = ?1")
    UserInfo findByUuid(String uuid);

    @Modifying
    @Transactional
    @Query("UPDATE UserInfo u SET u.is_verified = ?2 WHERE u.uuid = ?1")
    void updateUserInfoVerifyStatus(String uuid, Boolean isVerified);
}
