package com.skyproton.kyc_backend.repository;

import com.skyproton.kyc_backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AccountRepo extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.uuid = ?1")
    Account findByUuid(String uuid);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.status = ?2 WHERE a.uuid = ?1")
    void updateAccountStatusByUuid(String uuid, String status);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.user_info_uuid = ?2 WHERE a.uuid = ?1")
    void addUserInfoUuidToAccount(String accountUuid, String userInfoUuid);
}
