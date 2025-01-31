# To-Do App Backend

## Overview
This is the backend service for the To-Do App, built using **Spring Boot** to provide a RESTful API for managing tasks.
The backend handles the business logic such as filtering, sorting and pagination. 

## Features
- REST API to maange To-Dos (CRUD operations)
- Filtering and sorting capabilities
- Pagination support
- Unit tests with **Junit** and **Mockito**
- Architecture based on different layers: Controller, service and repository

## Technologies used
- **Spring Boot:** Framework for building the REST API
- **Spring Web:** Dependency that provides tools for handling HTTP requests
- **Spring validation:** Enables validation of user input
- **Junit:** For writing and running unit tests
- **Mockito:** For mocking dependencies in tests

## Prerequisites
Before running the project, make sure you have **JDK 17 or higher** installed.
This project was developed using JDK v23.0.1

### Check if JDK is installed:
```bash
java -version
```

## Running the backend
1. Install dependencies and build the project
```bash
mvn clean instal
```
2. Run the application
```bash
mvn spring-boot:run
```
3. The backend will be available at:
```bash
http://localhost:9090
```

## Running the tests
```bash
mvn test
```