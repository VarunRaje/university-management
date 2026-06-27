# University Management Service

Multi-module Spring Boot 4 REST microservice for university management.

## Modules

- `university-mgmt-common`: OpenAPI-generated REST interfaces and DTOs.
- `university-mgmt-dao`: JPA entities and Spring Data repositories.
- `university-mgmt-client`: RestClient-based client code.
- `university-mgmt-service`: Spring Boot application and business logic.

## Build

```bash
./gradlew clean build
```

## Integration Tests

The `integTest` task starts Postgres and Redis from `docker/docker-compose.integ-test.yml`.

```bash
./gradlew :university-mgmt-service:integTest
```

Keep containers running for inspection:

```bash
./gradlew :university-mgmt-service:integTest -PkeepIntegContainers
```

## Run Locally

```bash
docker compose --project-directory . -f docker/docker-compose.integ-test.yml up -d --wait
./gradlew :university-mgmt-service:bootRun
```

The REST API is served under `/api/v1`, including `/api/v1/health` and `/api/v1/info`.

## Docker Image

```bash
./gradlew :university-mgmt-service:bootJar
docker build -f docker/Dockerfile -t university-management-service:0.1.0-SNAPSHOT .
```
