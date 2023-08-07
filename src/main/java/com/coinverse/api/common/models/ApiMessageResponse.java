package com.coinverse.api.common.models;

import com.coinverse.api.common.constants.ApiMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ApiMessageResponse {
    private String message;

    public static ApiMessageResponse of(ApiMessage apiMessageEnum) {
        return new ApiMessageResponse(apiMessageEnum.getMessage());
    }
}
