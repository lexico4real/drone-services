# Spring Boot Application with MySQL using Docker Compose

This project demonstrates how to set up a Spring Boot application with MySQL using Docker Compose.

## Prerequisites

Before you begin, ensure you have the following tools installed on your machine:

- Docker
- Docker Compose
- Java Development Kit (JDK) 17
- Maven (for building the Spring Boot application)
- Postman

## Project Structure

```plaintext
drone-api/
│
├── src/
│   └── main/
|       └── com/
|           └── oluyinka/
|               └── drone-api/
|                   └── controllers/
|                   └── dto/
|                   └── entities/
|                   └── model/
|                   └── repositories/
|                   └── services/
|                   └── utils/
|                   └── DroneApplication.java
│
├── target/
│   └── drone-api-0.0.1-SNAPSHOT.jar
│
├── Dockerfile
├── docker-compose.yml
└── README.md
```
## Prerequisites

1.  Clone the repository to your local machine:
`git clone https://github.com/your-username/your-spring-boot-project.git drone-api
`
2.  Navigate to the project directory:
`cd drone-api
`
3.  Build the Spring Boot application:
`# For Maven
mvn clean install
`
4.  Create and start Docker containers using Docker Compose:
`docker-compose up --build
`
This command will build the Docker images and start the containers for MySQL and the Spring Boot application.
5.  Access the Spring Boot API:
Open your a tool like curl/Postman import the attached MUSALA.postman_collect.json file  to access the APIs.

## Spring Boot Application
The Spring Boot application is configured to connect to the MySQL database using the following environment variables:

SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD

Ensure that these variables match the configuration in the `docker-compose.yml` file.

## MySQL
The MySQL service is configured with the following environment variables:

MYSQL_ROOT_PASSWORD
MYSQL_DATABASE
MYSQL_USER
MYSQL_PASSWORD
Adjust these variables as needed.

## Troubleshooting
If you encounter issues, refer to the error messages in the logs and check the following:

Docker containers status: `docker-compose ps`
Logs for the Spring Boot application: `docker-compose logs spring-app`