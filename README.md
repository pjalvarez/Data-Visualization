# Data-Visualization

This project implements the Data-Visualization Microservice.

Once started the app tries to register at 'http://us1.fiwoo.eu:3331/eureka/' (Registry and Discovering Service). 

## Getting Started

### Prerequisites

- [Apache Maven 3.3.9]
- [Java 1.8]

## Build & Run

1. Run `mvn -U clean package` to install dependencies and generate WAR file.

2. Run `mvn spring-boot:run` 

3. The service will be available (local mode) at port 3333


## Testing

Running `mvn test` will run the unit tests with JUnit.

## Deployment with Docker

Pending

