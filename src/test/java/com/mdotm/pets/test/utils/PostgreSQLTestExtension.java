package com.mdotm.pets.test.utils;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLTestExtension implements BeforeAllCallback, AfterAllCallback {

    private final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13-alpine")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Override
    public void beforeAll(ExtensionContext context) {
        postgres.start();
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
        System.setProperty("spring.datasource.driver-class-name", "org.postgresql.Driver");
        System.setProperty("spring.jpa.database-platform", "org.hibernate.dialect.PostgreSQLDialect");
    }

    @Override
    public void afterAll(ExtensionContext context) {
        postgres.stop();
    }
}