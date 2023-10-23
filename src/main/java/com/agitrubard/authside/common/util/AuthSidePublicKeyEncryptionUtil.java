package com.agitrubard.authside.common.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class for public key encryption and decryption operations.
 * This class internally utilizes the AysDataEncryptionUtil class for encryption and decryption using a salt-based approach.
 * <p>
 * <p>
 * <b>How to generating salt?</b>
 * <p>
 * <b>First ></b> <a href="https://www.random.org/passwords/?num=5&len=32&format=html&rnd=new">Random Text Generator</a>
 * <p>
 * <b>Second ></b> <a href="https://www.base64encode.org/">Base 64 Encode</a>
 */
@UtilityClass
public class AuthSidePublicKeyEncryptionUtil {

    /**
     * The prefix of the salt used for encryption and decryption operations.
     */
    private static final String PREFIX_OF_SALT = "YXF6NndYd2VTN0pONzN0UXFVWVpxRnJ6Mm5HdUZkc1g=";

    /**
     * The postfix of the salt used for encryption and decryption operations.
     */
    private static final String POSTFIX_OF_SALT = "VHNnM3dLOUpZZko0clZRVE51QnZ1azRLVGp4QUxocHE=";

    /**
     * Encrypts the given raw data using the AysDataEncryptionUtil with the specified salt.
     *
     * @param rawData the raw data to be encrypted
     * @return the encrypted data
     */
    public static String encrypt(final String rawData) {
        return AuthSideDataEncryptionUtil.encrypt(rawData, PREFIX_OF_SALT, POSTFIX_OF_SALT);
    }

    /**
     * Decrypts the given encrypted data using the AysDataEncryptionUtil with the specified salt.
     *
     * @param encryptedData the encrypted data to be decrypted
     * @return the decrypted data
     */
    public static String decrypt(final String encryptedData) {
        return AuthSideDataEncryptionUtil.decrypt(encryptedData, PREFIX_OF_SALT, POSTFIX_OF_SALT);
    }

}
