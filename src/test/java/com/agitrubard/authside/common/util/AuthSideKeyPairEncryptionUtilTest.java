package com.agitrubard.authside.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The following resource was used to generate RSA key pairs in 2048-Bit length and PKCS #8 format:
 * <a href="https://www.csfieldguide.org.nz/en/interactives/rsa-key-generator/">Online RSA Key Pair Generator</a>
 */
@Slf4j
class AuthSideKeyPairEncryptionUtilTest {

    @Test
    void givenRawPrivateKey_whenPrivateKeyEncoded_thenReturnDecodedPrivateKey() {
        // Given
        String mockPEMFormattedPrivateKey = """
                -----BEGIN PRIVATE KEY-----
                MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDKP2N3PtwROAZq
                l7L5zEZz6b+HKukM/PsKfFqCwl2G/Zzi3ULrcaatpskhDypbavSqnhS4xS5su1OR
                5SdVNPoPRb2pJ2GA7bjB/+EXJ6SGl4YGNVAob4mjZ6+NusyMvVkIoQzxricVJw14
                EW6SVZnxfpVYjtJL3iY5q3w/aerLijy9UVwD6i6tBowq9BfhSgHvSIjqBPd82hGt
                lVy0Ie5qw9L1KtTfbTDbKmy7bwkDKj+i+sXpLm+jgwni8km0+QKzZx6NqRVa9am6
                yPyDAhbMm6HXCyuMVTTvQDGkrExAlsg0R6WP4wNxC7F8EXB6SRJSISv0W7yFdcvT
                S0qYW/YBAgMBAAECggEADhc4gNhaUMDMgaSsDYdT6UOB2pL8xbDidKqRS8bq6TrG
                MXAk925lpHeYT7tUwiXDghedbfoe7+hlPCbxBJi6S1IakqxxuJ/XXXzMW+ahKddo
                uQoB2a9bsYzJyGAZXDzc9SLzWes+QdUEtTbRs1RPc2nUUeUxdaO2mhH+iBSDDUcm
                mSMB6Na/gXuWGYUJq3q02rIKT6vDj6LujQEk0uoi3Z0A0TW3sp1ofkcVSGCRdAik
                +WV/eFq/lBbBbSEiZPqk98ayuTWSfkKnQZeQQohgrS3BnuzJjrUd65GKYpwr8jvs
                yyC4pALANibmsYaRabc1oNFpldYzPX4MZsJCVLlKAQKBgQDo6RBiRV+X8CphbYUq
                UKyGCteWxi85KUaxpdN9vHj4KoLPbYGckCPzAh3R8np73iRaadtHENhzRedvtgki
                zLCSCShbimp8tJ/aGemJgvdRLb5aBcQ6yULrvc/G3mpi/wSMPId0Lb+wFmq2USFq
                yutztCuAzKmD9EGJbh2SS5YE8QKBgQDeTCVxvEMHTnWzXkfWxdt5kjKLGxWXHQ0k
                J57IbOXAGs8P9vqwnCW+0Y9gZNQnhKrJaW0LBRCUQlc6a4/O/RTcoCwB7LBtmZmR
                C1p3tFEC+3ZMJwk9fUlrlF7tSfuSjfDt3TPQrvu66hjN141K2vNaqazfHWRXFaxc
                I/4AkhfCEQKBgCnxXqvzxkU8UMV+zY5TPN5esXhLdFGc50gyjqWD/Vzjeu8Tz1V8
                d5zZcrlOK1SHKpDFo2Fm3TPVENqDM536sol58LSbdpXCiU7jf7Ttys/ppu/bPf1A
                JTcC1ZoAj7QILK98bwAzBOQXBVyTu6rZpBEQDw2Azb4fjHXNSLIBvqBhAoGAb/65
                FrYcpv+2eOnVE7WXP4aShFbe58UxK7fyXVPP5JsdGxP5dSUWNy7pJ01lPYuvZ83/
                q/+fPLK3s60hRM1ox0bDJS0ULgOmxNaNe6WS8wexr+gy1ZHbfaHAuZf+cPWmHpDF
                XCvsk10BpiMlAkORO2okuDvco6s9rSZNAAiag6ECgYEA1wWJy+KsLjpaNe9Na/X3
                oOGMcnBJEavFnczKW9xo2SAQeFCVSTYRKTbyV8ulZkt3Fxcc5tih3V+L9MeOZpHI
                vuyz6PaBDs+1FZKNayxN75jyD4Fd1rIP++Y1pMo5p3pvkA7FaEhvVvSDylEpE94y
                ZpkT+luYms04gNe8125lzM4=
                -----END PRIVATE KEY-----
                """;
        log.info("Mock PEM Formatted Private Key: {}", mockPEMFormattedPrivateKey);

        String mockEncryptedPEMFormattedPrivateKey = AuthSidePrivateKeyEncryptionUtil.encrypt(mockPEMFormattedPrivateKey);
        log.info("Mock Encrypted PEM Formatted Public Key: {}", mockEncryptedPEMFormattedPrivateKey);

        // Then
        String decryptedPEMFormattedPrivateKey = AuthSidePrivateKeyEncryptionUtil.decrypt(mockEncryptedPEMFormattedPrivateKey);
        log.info("Decrypted PEM Formatted Private Key: {}\n\n", decryptedPEMFormattedPrivateKey);

        Assertions.assertEquals(mockPEMFormattedPrivateKey, decryptedPEMFormattedPrivateKey);
    }

    @Test
    void givenRawPublicKey_whenPublicKeyEncoded_thenReturnDecodedPublicKey() {
        // Given
        String mockPEMFormattedPublicKey = """
                -----BEGIN PUBLIC KEY-----
                MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyj9jdz7cETgGapey+cxG
                c+m/hyrpDPz7CnxagsJdhv2c4t1C63GmrabJIQ8qW2r0qp4UuMUubLtTkeUnVTT6
                D0W9qSdhgO24wf/hFyekhpeGBjVQKG+Jo2evjbrMjL1ZCKEM8a4nFScNeBFuklWZ
                8X6VWI7SS94mOat8P2nqy4o8vVFcA+ourQaMKvQX4UoB70iI6gT3fNoRrZVctCHu
                asPS9SrU320w2ypsu28JAyo/ovrF6S5vo4MJ4vJJtPkCs2cejakVWvWpusj8gwIW
                zJuh1wsrjFU070AxpKxMQJbINEelj+MDcQuxfBFwekkSUiEr9Fu8hXXL00tKmFv2
                AQIDAQAB
                -----END PUBLIC KEY-----
                """;
        log.info("Mock PEM Formatted Public Key: {}", mockPEMFormattedPublicKey);

        String mockEncryptedPEMFormattedPublicKey = AuthSidePublicKeyEncryptionUtil.encrypt(mockPEMFormattedPublicKey);
        log.info("Mock Encrypted PEM Formatted Public Key: {}", mockEncryptedPEMFormattedPublicKey);

        // Then
        String decryptedPEMFormattedPublicKey = AuthSidePublicKeyEncryptionUtil.decrypt(mockEncryptedPEMFormattedPublicKey);
        log.info("Decrypted PEM Formatted Public Key: {}\n\n", decryptedPEMFormattedPublicKey);

        Assertions.assertEquals(mockPEMFormattedPublicKey, decryptedPEMFormattedPublicKey);
    }

}
