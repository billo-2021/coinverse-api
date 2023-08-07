package com.coinverse.api.common.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class WalletKey {
    private String privateKey;
    private String publicKey;
    private String address;
}
