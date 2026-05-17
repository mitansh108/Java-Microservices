Im# Energy Expense Tracker

A microservices-based application for tracking home energy expenses, built with Spring Boot and JavaFX.

## Architecture

| Module | Description |
|--------|-------------|
| **Root project** | JavaFX desktop client (Java 21) |
| **user-service** | Spring Boot REST API for user management (Java 21, MySQL, Flyway) |

## Prerequisites

- Java 21
- Maven 3.9+
- Docker & Docker Compose

## Getting Started

### 1. Start the database

```bash
docker compose up -d
```

This starts a MySQL 8.3 container on port `3306` with:
- Database: `home_energy_tracker`
- Root password: `password`

### 2. Run the User Service

```bash
cd user-service
./mvnw spring-boot:run
```

### 3. Run the JavaFX Client

```bash
./mvnw clean javafx:run
```

## Stopping Services

```bash
docker compose down
```

To also remove persisted database data:

```bash
docker compose down -v
```

## Project Structure

```
energy-expense-tracker-java/
├── docker/
│   └── mysql/
│       └── init.sql            # DB initialization script
├── docker-compose.yml          # MySQL container config
├── user-service/               # Spring Boot user microservice
│   ├── src/
│   └── pom.xml
├── src/                        # JavaFX desktop client
│   └── main/
│       ├── java/
│       └── resources/
└── pom.xml                     # Root POM (JavaFX client)
```

## Configuration

The user-service requires database connection properties. Add the following to `user-service/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/home_energy_tracker
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true
```

## Tech Stack

- **Java 21**
- **Spring Boot 4.0** — REST API, JPA, Actuator, Flyway
- **JavaFX 21** — Desktop UI
- **MySQL 8.3** — Relational database
- **Flyway** — Database migrations
- **Lombok** — Boilerplate reduction
- **Micrometer + Brave** — Distributed tracing
