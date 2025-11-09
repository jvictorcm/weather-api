# Weather-api (I know, the name is terrible haha)

This project is a Spring Boot application that can be run using the provided Docker Compose file and gradle task bootRun.

## Requirements

- JDK 17
- gradle 7.6.4
- Docker
- Docker Compose

## Running the Application

To run the application using Docker Compose, follow these steps:

1. Clone the repository to your local machine.
2. Open a terminal window and navigate to the root directory of the project.
3. Run the following command to build the required database:
    ```docker-compose up```
4. Run the task gradle bootRun: ```gradlew clean build bootRun```
5. Once the application is running, you can access the Swagger UI by opening your web browser and navigating to the following URL: http://localhost:8080/swagger-ui/index.html#/
   (date value formatted 2023-02-12T23:55:12.000-00:00)

## Test Coverage
* This project includes unit tests and generates Jacoco test coverage reports at ```build/reports/jacoco/test/html/index.html```
* This project includes integrationTests.
* ```gradlew clean test integrationTest```

## Functionality
* This project has two endpoints.
  * POST to create the sensorMetric for a sensor
  * GET to get sensorMetrics where you can choose what kind of sensorMetrics (average, max or min) to a given set of sensors and to a given(or not) range of dates (that work with either start date or end date)
* This project feature traceId and spanId to easy debugging process in production in cluster environments.
* Has capability to get Spring Profiles as is created a dev profile at [application-dev.yaml](src/main/resources/application-dev.yaml) 

## Next Steps for this Project
* Refactor to better namings
* Improve Swagger Docs
* Proper Input Validation(validation layer)
* Proper Exception Handling (using ResponseEntityExceptionHandler) to handle specific exceptions created, for example, to do not allow negative wind speed or out of the range for the temperature and return with valid response codes and body.
* Create Dockerfile
* Create docker-compose file with the application building within
* Add newrelic sensorMetrics to collect relevant information such as most required sensor data for the purpose of measure future caching features.
* Have fun! :)