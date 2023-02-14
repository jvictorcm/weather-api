package br.com.victor.weatherapi.rest;

import br.com.victor.weatherapi.configuration.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HealthCheckScenarios extends AbstractIntegrationTest {

    @Test
    public void shouldReturnHealth() {
        var spec = new RequestSpecBuilder().build();

        given()
                .when()
                .spec(spec)
                .log()
                .all()
                .get("/actuator/health")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("status", equalTo("UP"));
    }
}
