package br.com.victor.weatherapi.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;

public class ContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger LOG = LoggerFactory.getLogger(ContainerInitializer.class);

    private  PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres:15.1")
            .withDatabaseName("weatherdb")
            .withUsername("user")
            .withPassword("user");

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        LOG.info("###### Starting Containers ######");
        postgresContainer.withExposedPorts(5432).start();
        LOG.info("***** Postgresql Started {} *****", postgresContainer.getJdbcUrl());

        String databaseURL = "spring.datasource.url=" + postgresContainer.getJdbcUrl() + "?useSSL=false";
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext, databaseURL);
    }
}
