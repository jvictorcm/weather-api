# Weather-api (I know, the name is terrible haha)

This project is a Spring Boot application that can be run using the provided Docker Compose file and gradle task bootRun.

## Requirements

- JDK 11
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
* This project includes integrationTests to validate schemas.
* There's a dependency between Unit and Integration tests to be always ran in the building process.

## Functionality
* This project has two endpoints.
  * POST to create the metric for a sensor (the way it was meant to be created, giving flexibility to the evolution of this domain)
  * GET to get metrics where you can choose what kind of metrics (average, max or min) to a given(or not) set of sensors and to a given(or not) range of dates (that work with either start date or end date)

## To Evolve this project
* Transition this into an Event Driven + CQRS architecture given the complexity of querying all data, moving the GET endpoint to a reactive service. The way that the model has been created and the interface from the client, was meant to facilitate this process.
* Add newrelic metrics to collect relevant information such as most required sensor data for the purpose of measure future caching features.
* Increase Integration Test coverage
* Increase Swagger Docs
* Add a new statistic for SUM values of the queried sensors (missed this one, sorry)
* Proper Input Validation(validation layer to be used in the service layer)
* Proper Exception Handling (using ResponseEntityExceptionHandler) to handle specific exceptions created, for example, to do not allow negative wind speed or out of the range for the temperature and return with valid response codes and body.
* Have fun! :)