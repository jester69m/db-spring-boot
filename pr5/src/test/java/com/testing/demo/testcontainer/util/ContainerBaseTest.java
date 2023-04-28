package com.testing.demo.testcontainer.util;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.TestSocketUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataJpaTest(properties = {"spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect",
        "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = ContainerBaseTest.Initializer.class)
public class ContainerBaseTest {

    @Container
    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest");

    static {
        container.start();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                            "spring.datasource.url=" + container.getJdbcUrl().replaceFirst("jdbc", "jdbc:tc"),
                            "spring.datasource.username=" + container.getUsername(),
                            "spring.datasource.password=" + container.getPassword(),
                            "local.server.port=" + TestSocketUtils.findAvailableTcpPort()
                    )
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
