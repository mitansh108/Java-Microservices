#       Energy Expense Tracker

A microservices-based backend for tracking home energy consumption in real time. Built with **Spring Boot 4**, **Apache Kafka**, and **MySQL**, following an event-driven architecture.

## Architecture

```
┌─────────────────────┐
│   JavaFX Desktop    │
│      Client         │
└────────┬────────────┘
         │ REST
         ▼
┌─────────────────────┐     ┌─────────────────────┐
│    User Service     │     │   Device Service     │
│   (CRUD + Auth)     │     │   (CRUD Devices)     │
│    Port: 8080       │     │    Port: 8081         │
└────────┬────────────┘     └────────┬─────────────┘
         │                           │
         ▼                           ▼
┌──────────────────────────────────────────────────┐
│                    MySQL 8.3                      │
│              home_energy_tracker                  │
└──────────────────────────────────────────────────┘

┌─────────────────────┐
│  Parallel Data      │        ┌──────────────┐
│  Simulator          │───────▶│  Ingestion   │
│  (10 threads,       │  REST  │  Service     │
│   1000 req/5s)      │        │  Port: 8082  │
└─────────────────────┘        └──────┬───────┘
                                      │ Produce
                                      ▼
                            ┌──────────────────┐
                            │   Apache Kafka   │
                            │  (KRaft mode)    │
                            │  Topic:          │
                            │  energy-usage    │
                            └──────────────────┘
                                      │
                                      ▼
                               Consumer Service
                                  (planned)
```

## Services

| Service | Description | Port |
|---------|-------------|------|
| **user-service** | User registration and management (REST API, JPA, Flyway migrations) | 8080 |
| **device-service** | CRUD operations for household energy devices (REST API, JPA, Flyway) | 8081 |
| **ingestion-service** | Receives energy usage data via REST, publishes events to Kafka | 8082 |

## Tech Stack

- **Java 21** — Language
- **Spring Boot 4.0.6** — Microservices framework
- **Apache Kafka (KRaft)** — Event streaming (no ZooKeeper)
- **MySQL 8.3** — Relational database
- **Flyway** — Database migrations
- **Lombok** — Boilerplate reduction
- **Jackson** — JSON serialization (with JSR-310 for Java time types)
- **JavaFX 21** — Desktop UI client
- **Docker Compose** — Local infrastructure (MySQL, Kafka, Kafka UI)

## Prerequisites

- Java 21
- Maven 3.9+
- Docker & Docker Compose

## Getting Started

### 1. Start infrastructure

```bash
docker compose up -d
```

This starts:
- **MySQL 8.3** on port `3306` (database: `home_energy_tracker`, password: `password`)
- **Kafka** on port `9094` (external) / `9092` (internal)
- **Kafka UI** on port `8070` — browse topics at [http://localhost:8070](http://localhost:8070)

### 2. Run the services

Each service runs independently from its own directory:

```bash
# User Service
cd user-service && ./mvnw spring-boot:run

# Device Service
cd device-service && ./mvnw spring-boot:run

# Ingestion Service (starts parallel simulator automatically)
cd ingestion-service && ./mvnw spring-boot:run
```

### 3. Run the JavaFX client (optional)

```bash
./mvnw clean javafx:run
```

## API Endpoints

### User Service (port 8080)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/users` | Create user |
| GET | `/api/v1/users` | List all users |
| GET | `/api/v1/users/{id}` | Get user by ID |
| PUT | `/api/v1/users/{id}` | Update user |
| DELETE | `/api/v1/users/{id}` | Delete user |

### Device Service (port 8081)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/devices` | Register device |
| GET | `/api/v1/devices` | List all devices |
| GET | `/api/v1/devices/{id}` | Get device by ID |
| PUT | `/api/v1/devices/{id}` | Update device |
| DELETE | `/api/v1/devices/{id}` | Delete device |

### Ingestion Service (port 8082)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/ingestion/energy-usage` | Ingest energy usage event |

**Sample request:**
```bash
curl -X POST http://localhost:8082/api/v1/ingestion/energy-usage \
  -H "Content-Type: application/json" \
  -d '{"deviceId": 1, "energyConsumed": 25.5, "timestamp": "2026-05-17T10:30:00Z"}'
```

## Kafka Events

The ingestion service publishes `EnergyUsageEvent` to the `energy-usage` topic:

```json
{
  "deviceId": 3,
  "energyConsumed": 1.47,
  "timestamp": "2026-05-17T23:31:53Z"
}
```

The **Parallel Data Simulator** runs embedded in the ingestion service, generating mock data with 10 concurrent threads sending 1000 events every 5 seconds.

## Project Structure

```
energy-expense-tracker-java/
├── docker/
│   └── mysql/init.sql                 # DB initialization
├── docker-compose.yml                 # MySQL + Kafka + Kafka UI
├── user-service/                      # User management microservice
├── device-service/                    # Device management microservice
├── ingestion-service/                 # Kafka producer + data simulator
│   └── src/main/java/.../
│       ├── controller/                # REST endpoint
│       ├── service/                   # Kafka publishing logic
│       ├── dto/                       # Request DTOs
│       ├── kakfa/event/               # Kafka event models
│       └── simulation/                # Parallel data simulator
├── src/                               # JavaFX desktop client
└── pom.xml                            # Root POM
```

## Stopping Services

```bash
docker compose down        # Stop containers
docker compose down -v     # Stop + remove data volumes
```
