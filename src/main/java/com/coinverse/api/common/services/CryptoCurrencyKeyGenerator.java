package com.coinverse.api.common.services;

import com.coinverse.api.common.models.CryptoCurrencyKey;
import lombok.RequiredArgsConstructor;
import org.bitcoinj.base.Base58;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECPoint;

@Component
@RequiredArgsConstructor
public class CryptoCurrencyKeyGenerator {
    public CryptoCurrencyKey generate() {
        try {
            KeyPairGenerator keyGen =  KeyPairGenerator.getInstance("EC", "SunEC");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
            keyGen.initialize(ecSpec, new SecureRandom());

            KeyPair keyPair = keyGen.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            ECPrivateKey ecPrivateKey = (ECPrivateKey) privateKey;
            String privateKeyStr = adjustTo64(ecPrivateKey.getS().toString(16));

            ECPublicKey ecPublicKey = (ECPublicKey) publicKey;
            ECPoint ecPoint = ecPublicKey.getW();
            String sx = adjustTo64(ecPoint.getAffineX().toString(16)).toUpperCase();
            String sy = adjustTo64(ecPoint.getAffineY().toString(16)).toUpperCase();
            String publicKeyStr = "04" + sx + sy;

            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] s1 = sha.digest(publicKeyStr.getBytes(StandardCharsets.UTF_8));
            MessageDigest rmd = MessageDigest.getInstance("RipeMD160", "BC");
            byte[] r1 = rmd.digest(s1);

            byte[] r2 = new byte[r1.length + 1];
            r2[0] = 0;

            System.arraycopy(r1, 0, r2, 1, r1.length);

            byte[] s2 = sha.digest(r2);
            byte[] s3 = sha.digest(s2);

            byte[] a1 = new byte[25];
            System.arraycopy(r2, 0, a1, 0, r2.length);

            System.arraycopy(s3, 0, a1, 20, 5);

            String address = Base58.encode(a1);

            return new CryptoCurrencyKey(privateKeyStr, publicKeyStr, address);
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchProviderException e) {
            throw new RuntimeException("Unable to generate key ", e);
        }
    }

    private static String adjustTo64(String s) {
        return switch (s.length()) {
            case 62 -> "00" + s;
            case 63 -> "0" + s;
            case 64 -> s;
            default -> throw new IllegalArgumentException("Not a valid key: " + s);
        };
    }
}
