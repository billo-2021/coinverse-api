package com.coinverse.api.common.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountResponse {
    @JsonIgnore
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private AccountStatusEnum status;
    @JsonIgnore
    private Boolean isEnabled;
}
