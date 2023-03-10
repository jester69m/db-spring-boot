package com.testing.demo.testcontainer.util;

import com.testing.demo.EmployeeRepository;
import org.hibernate.dialect.PostgreSQLDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.TestSocketUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.Base58;

@SpringBootTest
@ContextConfiguration(initializers = {BaseTest.Initializer.class})
public class BaseTest {

    @Autowired
    protected EmployeeRepository repository;

    private static final PostgreSQLContainer<?> POSTGRES
            = new PostgreSQLContainer<>("postgres")
            .withUsername("postgres-docker")
            .withPassword("password")
            .withDatabaseName("postgres")
            .withNetworkAliases("postgres - "+ Base58.randomString(6));

    static{
        POSTGRES.start();
    }
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{

        public void initialize(ConfigurableApplicationContext configurableApplicationContext){
            TestPropertyValues.of(
                    "spring.datasource.url"+ POSTGRES.getJdbcUrl(),
                    "spring.datasource.username"+POSTGRES.getUsername(),
                    "spring.datasource.password" + POSTGRES.getPassword(),
                    "local.server.ports"+ TestSocketUtils.findAvailableTcpPort()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
