package br.com.victor.weatherapi.configuration;

import br.com.victor.weatherapi.WeatherApiApplication;
import br.com.victor.weatherapi.infrastructure.ContainerInitializer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

@ActiveProfiles("integration-test")
@ContextConfiguration(initializers = ContainerInitializer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WeatherApiApplication.class)
@AutoConfigureWireMock(port = WireMockConfiguration.DYNAMIC_PORT)
public class AbstractIntegrationTest {
    @Value("${local.server.port}")
    private Integer port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

}
