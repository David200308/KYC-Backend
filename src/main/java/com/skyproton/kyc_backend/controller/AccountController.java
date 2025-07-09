package com.skyproton.kyc_backend.controller;

import com.skyproton.kyc_backend.dto.account.ReqAccountDTO;
import com.skyproton.kyc_backend.dto.account.ReqUpdateAccountStatusDTO;
import com.skyproton.kyc_backend.dto.account.ResAccountDTO;
import com.skyproton.kyc_backend.dto.ResUpdateDTO;
import com.skyproton.kyc_backend.service.AccountService;
import com.skyproton.kyc_backend.variable.AccountStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<ResAccountDTO> createAccount(@RequestBody ReqAccountDTO reqAccountDTO) {
        ResAccountDTO createdAccountRes = accountService.createAccount(reqAccountDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccountRes);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ResAccountDTO> getAccountByUuid(@PathVariable String uuid) {
        try {
            ResAccountDTO account = accountService.getAccountByUuid(uuid);
            return ResponseEntity.ok(account);
        } catch (RuntimeException e) {
            switch (e.getMessage()) {
                case "ACCOUNT_NOT_FOUND":
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                default:
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @PatchMapping("/{uuid}/status")
    public ResponseEntity<ResUpdateDTO> updateAccountStatus(@PathVariable String uuid, @RequestBody ReqUpdateAccountStatusDTO data) {
        ResUpdateDTO res = accountService.updateAccountStatus(uuid, data.getStatus());
        if (!res.getIs_success()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        return ResponseEntity.ok(res);
    }

}
