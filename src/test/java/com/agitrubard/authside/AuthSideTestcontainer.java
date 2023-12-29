package com.agitrubard.authside;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

interface AuthSideTestcontainer {

    MySQLContainer MYSQL_CONTAINER = new MySQLContainer("mysql:8.0.33")
            .withUsername("auth_side")
            .withPassword("auth_side_pass")
            .withDatabaseName("test");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry dynamicPropertyRegistry) {
        MYSQL_CONTAINER.start();
        dynamicPropertyRegistry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
        dynamicPropertyRegistry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
    }

}
