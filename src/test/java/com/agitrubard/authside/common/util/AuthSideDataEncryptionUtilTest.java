package com.agitrubard.authside.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class AuthSideDataEncryptionUtilTest {

    @Test
    void givenMockRawData_whenEncodedData_thenReturnEncodedData() {
        log.info("givenMockRawData_whenEncodedData_thenReturnEncodedData Test:");
        // Given
        String mockRawData = "Hello World!";
        log.info("mockRawData: {}", mockRawData);

        // Then
        String encodedData = AuthSideDataEncryptionUtil.encrypt(mockRawData);
        log.info("encodedData: {}\n", encodedData);
    }

    @Test
    void givenEncodedData_whenDecodedData_thenReturnDecodedData() {
        log.info("givenEncodedData_whenDecodedData_thenReturnDecodedData Test:");
        // Given
        String mockRawData = "Hello World!";
        log.info("mockRawData: {}", mockRawData);

        String encodedData = AuthSideDataEncryptionUtil.encrypt(mockRawData);
        log.info("encodedData: {}", encodedData);

        // Then
        String decodedData = AuthSideDataEncryptionUtil.decrypt(encodedData);
        log.info("decodedData: {}\n", decodedData);

        Assertions.assertEquals(mockRawData, decodedData);
    }

}