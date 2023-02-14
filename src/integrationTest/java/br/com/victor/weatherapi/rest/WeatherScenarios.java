package br.com.victor.weatherapi.rest;

import br.com.victor.weatherapi.configuration.AbstractIntegrationTest;
import br.com.victor.weatherapi.utils.FileUtils;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class WeatherScenarios extends AbstractIntegrationTest {

    @Test
    public void shouldReturnCreatedWeatherMetric() throws JSONException {
        var spec = new RequestSpecBuilder().build();
        String bodyValue = postBodyCreator();
        given()
                .when()
                .spec(spec)
                .log()
                .all()
                .body(bodyValue)
                .contentType(ContentType.JSON)
                .post("/weathers")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body(JsonSchemaValidator.matchesJsonSchema(FileUtils.getResourceFileAsString("jsonSchemaMetric.json")));
    }

    @Test
    public void shouldReturnCreatedWeatherMetricWithMissingParameter() throws JSONException {
        var spec = new RequestSpecBuilder().build();
        String bodyValue = postBodyCreatorWithMissingParameter();
        given()
                .when()
                .spec(spec)
                .log()
                .all()
                .body(bodyValue)
                .contentType(ContentType.JSON)
                .post("/weathers")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body(JsonSchemaValidator.matchesJsonSchema(FileUtils.getResourceFileAsString("jsonSchemaMetricWithMissingParameter.json")));
    }


    private static String postBodyCreator() throws JSONException {
        /*
        {
            "sensorId":"1234",
            "metrics":{
                "temperature":10,
                "humidity":20,
                "windSpeed":30
            }
         */
        JSONObject request = new JSONObject();
        request.put("sensorId", "1234");
        JSONObject requestMetrics = new JSONObject();
        requestMetrics.put("temperature", 10);
        requestMetrics.put("humidity", 20);
        requestMetrics.put("windSpeed", 30);
        request.put("metrics", requestMetrics);
        return request.toString();
    }

    private static String postBodyCreatorWithMissingParameter() throws JSONException {
        /*
        {
            "sensorId":"1234",
            "metrics":{
                "temperature":10,
                "humidity":20
            }
         */
        JSONObject request = new JSONObject();
        request.put("sensorId", "1234");
        JSONObject requestMetrics = new JSONObject();
        requestMetrics.put("temperature", 10);
        requestMetrics.put("humidity", 20);
        request.put("metrics", requestMetrics);
        return request.toString();
    }


}
