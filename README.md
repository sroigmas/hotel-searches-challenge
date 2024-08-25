# Hotel Searches Challenge

### Requirements

- Java 21
- Docker

### Notes

- The description wasn't clear about the criteria for the count, so it's being performed matching by
  hotelId, checkIn, checkOut and number of people (ages array size).
  A compound index is automatically created to improve the performance of the count query
- Hexagonal architecture is being used. Spring has been considered part of the infrastructure so
  there are no Spring annotations inside the 'application' folder.
  Use cases are being initialized inside 'infrastructure/configuration' folder
- Java records are being used for immutability
- MongoDB was chosen for the persistence
- Kafka events are defined with Avro. Schema Registry is being used for this purpose
- Spring Boot Docker Compose support is being used. Docker containers (for Mongo and Kafka) are
  executed just by running the Spring application
- Integration testing is done with Testcontainers

### Improvements

- More tests could be added

## How to run

````bash
./mvnw clean compile
./mvnw spring-boot:run
````

You can check the OpenAPI documentation:

- Using Swagger UI: http://localhost:8080/swagger-ui/index.html
- In JSON format: http://localhost:8080/v3/api-docs
- In YAML format: http://localhost:8080/v3/api-docs.yaml

## How to run tests

````bash
./mvnw verify
````
