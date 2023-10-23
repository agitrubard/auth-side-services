package com.agitrubard.authside.common.util;

import com.agitrubard.authside.common.application.exception.AuthSideKeyGenerationException;
import com.agitrubard.authside.common.application.exception.AuthSideKeyReadException;
import com.agitrubard.authside.common.application.exception.AuthSideUnexpectedArgumentException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.IOException;
import java.io.StringReader;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * Utility class for working with RSA Key Pairs in PEM format.
 * This class provides methods for generating key pairs, formatting them in PEM format,
 * and converting between PEM format and Java `PrivateKey` and `PublicKey` objects.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0.0
 */
@UtilityClass
public class AuthSideKeyPairUtil {

    /**
     * Generates an RSA Key Pair.
     *
     * @return The generated RSA Key Pair.
     * @throws AuthSideKeyGenerationException if an error occurs during key pair generation.
     */
    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception exception) {
            throw new AuthSideKeyGenerationException(exception);
        }
    }

    /**
     * Converts a PrivateKey to PEM format and encrypts it.
     *
     * @param privateKey The PrivateKey to convert and encrypt.
     * @return The PEM formatted and encrypted PrivateKey.
     */
    public static String getPemFormattedEncryptedPrivateKey(final PrivateKey privateKey) {
        String pemFormattedPrivateKey = convertToPEM(privateKey);
        return AuthSidePrivateKeyEncryptionUtil.encrypt(pemFormattedPrivateKey);
    }

    /**
     * Converts a PublicKey to PEM format and encrypts it.
     *
     * @param publicKey The PublicKey to convert and encrypt.
     * @return The PEM formatted and encrypted PublicKey.
     */
    public static String getPemFormattedEncryptedPublicKey(final PublicKey publicKey) {
        String pemFormattedPrivateKey = convertToPEM(publicKey);
        return AuthSidePublicKeyEncryptionUtil.encrypt(pemFormattedPrivateKey);
    }

    /**
     * Converts a Key to PEM format.
     *
     * @param key The Key to convert to PEM format.
     * @return The PEM formatted representation of the given Key.
     */
    private static String convertToPEM(final Key key) {
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

    /**
     * Converts a PEM formatted and encrypted PrivateKey to a PrivateKey object.
     *
     * @param pemFormattedEncryptedPrivateKey The PEM formatted and encrypted PrivateKey.
     * @return The PrivateKey object.
     * @throws AuthSideKeyReadException if an error occurs during the conversion.
     */
    public static PrivateKey convertPrivateKey(String pemFormattedEncryptedPrivateKey) {
        final String decryptedPrivateKeyPem = AuthSidePrivateKeyEncryptionUtil.decrypt(pemFormattedEncryptedPrivateKey);
        final String formattedPrivateKeyPem = decryptedPrivateKeyPem.replace("             ", "\n");
        final StringReader keyReader = new StringReader(formattedPrivateKeyPem);
        try {
            PrivateKeyInfo privateKeyInfo = PrivateKeyInfo
                    .getInstance(new PEMParser(keyReader).readObject());
            return new JcaPEMKeyConverter().getPrivateKey(privateKeyInfo);
        } catch (IOException exception) {
            throw new AuthSideKeyReadException(exception);
        }
    }

    /**
     * Converts a PEM formatted and encrypted PublicKey to a PublicKey object.
     *
     * @param pemFormattedEncryptedPublicKey The PEM formatted and encrypted PublicKey.
     * @return The PublicKey object.
     * @throws AuthSideKeyReadException if an error occurs during the conversion.
     */
    public static PublicKey convertPublicKey(String pemFormattedEncryptedPublicKey) {
        final String decryptedPublicKeyPem = AuthSidePublicKeyEncryptionUtil.decrypt(pemFormattedEncryptedPublicKey);
        final String formattedPublicKeyPem = decryptedPublicKeyPem.replace("             ", "\n");
        StringReader keyReader = new StringReader(formattedPublicKeyPem);
        try {
            SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo
                    .getInstance(new PEMParser(keyReader).readObject());
            return new JcaPEMKeyConverter().getPublicKey(publicKeyInfo);
        } catch (IOException exception) {
            throw new AuthSideKeyReadException(exception);
        }
    }


    /**
     * An enum representing the type of KeyPair (Private Key or Public Key).
     */
    @Getter
    @RequiredArgsConstructor
    private enum KeyPairType {
        PRIVATE_KEY("PRIVATE KEY"),
        PUBLIC_KEY("PUBLIC KEY");

        private final String value;

        /**
         * Determines the KeyPairType from a given Key object.
         *
         * @param key The Key object to determine its type.
         * @return The KeyPairType based on the given Key object.
         * @throws AuthSideUnexpectedArgumentException if the Key object is not a valid PrivateKey or PublicKey.
         */
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
