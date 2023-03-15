package com.testing.demo.testcontainer.util;

import com.testing.demo.EmployeeRepository;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.TestSocketUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.Base58;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = {BaseTest.Initializer.class})
public class BaseTest {

    @ClassRule
    private static final PostgreSQLContainer<?> POSTGRES
            = new PostgreSQLContainer<>("postgres-docker")
            .withUsername("postgres")
            .withPassword("password")
            .withDatabaseName("pr5");
    static{
        POSTGRES.start();
    }
    @Configuration
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{

        public void initialize(ConfigurableApplicationContext configurableApplicationContext){
            TestPropertyValues.of(
                    "spring.datasource.url"+ POSTGRES.getJdbcUrl(),
                    "spring.datasource.username"+POSTGRES.getUsername(),
                    "spring.datasource.password" + POSTGRES.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
