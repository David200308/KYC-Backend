package com.skyproton.kyc_backend.mapper;

import com.skyproton.kyc_backend.dto.ResUpdateDTO;
import com.skyproton.kyc_backend.variable.UpdateField;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public ResUpdateDTO mapToResUpdateDTO(
            String uuid,
            UpdateField field,
            Boolean isSuccess,
            String message
    ) {
        ResUpdateDTO resUpdateAccountDTO = new ResUpdateDTO();
        resUpdateAccountDTO.setUuid(uuid);
        resUpdateAccountDTO.setField(field.getField());
        resUpdateAccountDTO.setIs_success(isSuccess);
        resUpdateAccountDTO.setMessage(message);

        return resUpdateAccountDTO;
    }
}
