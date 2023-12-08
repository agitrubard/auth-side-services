package com.agitrubard.authside.common.util;

import com.agitrubard.authside.common.application.exception.AuthSideKeyGenerationException;
import com.agitrubard.authside.common.application.exception.AuthSideKeyReadException;
import com.agitrubard.authside.common.application.exception.AuthSideUnexpectedArgumentException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@Slf4j
class AuthSideKeyPairUtilTest {

    @Test
    void whenGeneratedRSA2048KeyPair_thenReturnKeyPair() throws NoSuchAlgorithmException {
        // Initialize
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair mockKeyPair = keyPairGenerator.generateKeyPair();

        // Then
        KeyPair keyPair = AuthSideKeyPairUtil.generateKeyPair();

        Assertions.assertEquals(mockKeyPair.getPrivate().getAlgorithm(), keyPair.getPrivate().getAlgorithm());
        Assertions.assertEquals(mockKeyPair.getPrivate().getFormat(), keyPair.getPrivate().getFormat());
        Assertions.assertEquals(mockKeyPair.getPublic().getAlgorithm(), keyPair.getPublic().getAlgorithm());
        Assertions.assertEquals(mockKeyPair.getPublic().getFormat(), keyPair.getPublic().getFormat());
    }

    @Test
    void whenNotGeneratedKeyPair_thenThrowAuthSideKeyGenerationException() throws NoSuchAlgorithmException {
        try (MockedStatic<KeyPairGenerator> mockito = Mockito.mockStatic(KeyPairGenerator.class)) {

            // When
            mockito.when((MockedStatic.Verification) KeyPairGenerator.getInstance("ABC"))
                    .thenThrow(NoSuchAlgorithmException.class);

            // Then
            Assertions.assertThrows(
                    AuthSideKeyGenerationException.class,
                    AuthSideKeyPairUtil::generateKeyPair
            );
        }
    }


    @Test
    void givenValidPrivateKey_whenFormattedAndEncryptedPrivateKey_thenReturnPemFormattedEncryptedPrivateKey() throws NoSuchAlgorithmException {
        // Initialize
        PrivateKey mockPrivateKey = AuthSideKeyPairUtil.generateKeyPair().getPrivate();

        final String encoded = Base64.getEncoder().encodeToString(mockPrivateKey.getEncoded());
        final StringBuilder pem = new StringBuilder();
        pem.append("-----BEGIN ").append("PRIVATE KEY").append("-----\n");
        for (int i = 0; i < encoded.length(); i += 64) {
            pem.append(encoded, i, Math.min(encoded.length(), i + 64)).append("\n");
        }
        pem.append("-----END ").append("PRIVATE KEY").append("-----\n");
        String mockPemFormattedEncryptedPrivateKey = AuthSidePrivateKeyEncryptionUtil.encrypt(pem.toString());

        // Then
        String pemFormattedEncryptedPrivateKey = AuthSideKeyPairUtil.getPemFormattedEncryptedPrivateKey(mockPrivateKey);

        Assertions.assertEquals(mockPemFormattedEncryptedPrivateKey, pemFormattedEncryptedPrivateKey);
    }


    @Test
    void givenValidPublicKey_whenFormattedAndEncryptedPublicKey_thenReturnPemFormattedEncryptedPublicKey() throws NoSuchAlgorithmException {
        // Initialize
        PublicKey mockPublicKey = AuthSideKeyPairUtil.generateKeyPair().getPublic();
        String mockPemFormattedPublicKey = this.convertToPEM(mockPublicKey);
        String mockPemFormattedEncryptedPublicKey = AuthSidePublicKeyEncryptionUtil.encrypt(mockPemFormattedPublicKey);

        // Then
        String pemFormattedEncryptedPublicKey = AuthSideKeyPairUtil.getPemFormattedEncryptedPublicKey(mockPublicKey);

        Assertions.assertEquals(mockPemFormattedEncryptedPublicKey, pemFormattedEncryptedPublicKey);
    }


    @Test
    void givenValidPemFormattedEncryptedPrivateKey_whenConverted_thenReturnPrivateKey() {
        // Initialize
        PrivateKey mockPrivateKey = AuthSideKeyPairUtil.generateKeyPair().getPrivate();
        String mockPemFormattedPrivateKey = this.convertToPEM(mockPrivateKey);
        String mockPemFormattedEncryptedPrivateKey = AuthSidePrivateKeyEncryptionUtil.encrypt(mockPemFormattedPrivateKey);

        // Then
        PrivateKey privateKey = AuthSideKeyPairUtil.convertPrivateKey(mockPemFormattedEncryptedPrivateKey);

        Assertions.assertEquals(mockPrivateKey, privateKey);
    }

    @Test
    void givenInvalidPemFormattedEncryptedPrivateKey_whenConverted_thenReturnPrivateKey() {
        // Initialize
        String mockPemFormattedEncryptedPrivateKey = "Invalid";

        // Then
        Assertions.assertThrows(
                AuthSideKeyReadException.class,
                () -> AuthSideKeyPairUtil.convertPrivateKey(mockPemFormattedEncryptedPrivateKey)
        );
    }


    @Test
    void givenValidPemFormattedEncryptedPublicKey_whenConverted_thenReturnPublicKey() {
        // Initialize
        PublicKey mockPublicKey = AuthSideKeyPairUtil.generateKeyPair().getPublic();
        String mockPemFormattedPublicKey = this.convertToPEM(mockPublicKey);
        String mockPemFormattedEncryptedPublicKey = AuthSidePublicKeyEncryptionUtil.encrypt(mockPemFormattedPublicKey);

        // Then
        PublicKey publicKey = AuthSideKeyPairUtil.convertPublicKey(mockPemFormattedEncryptedPublicKey);

        Assertions.assertEquals(mockPublicKey, publicKey);
    }

    @Test
    void givenInvalidPemFormattedEncryptedPublicKey_whenConverted_thenReturnPublicKey() {
        // Initialize
        String mockPemFormattedEncryptedPublicKey = "Invalid";

        // Then
        Assertions.assertThrows(
                AuthSideKeyReadException.class,
                () -> AuthSideKeyPairUtil.convertPublicKey(mockPemFormattedEncryptedPublicKey)
        );
    }


    private String convertToPEM(final Key key) {
        final KeyPairType keyPairType = KeyPairType.valueOf(key);
        final String encoded = Base64.getEncoder().encodeToString(key.getEncoded());
        final StringBuilder pem = new StringBuilder();
        pem.append("-----BEGIN ").append(keyPairType.getValue()).append("-----\n");
        for (int i = 0; i < encoded.length(); i += 64) {
            pem.append(encoded, i, Math.min(encoded.length(), i + 64)).append("\n");
        }
        pem.append("-----END ").append(keyPairType.getValue()).append("-----\n");
        return pem.toString();
    }

    @Getter
    @RequiredArgsConstructor
    private enum KeyPairType {
        PRIVATE_KEY("PRIVATE KEY"),
        PUBLIC_KEY("PUBLIC KEY");

        private final String value;

        public static KeyPairType valueOf(Key key) {

            if (key instanceof PrivateKey) {
                return PRIVATE_KEY;
            }

            if (key instanceof PublicKey) {
                return PUBLIC_KEY;
            }

            throw new AuthSideUnexpectedArgumentException(key);
        }

    }

}
