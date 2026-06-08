# Task Manager API

A RESTful task management API built with **Spring Boot 3**, **Spring Data JPA**, and **H2** (swappable for PostgreSQL/MySQL). Demonstrates full CRUD operations, layered architecture (Controller → Service → Repository), input validation, global exception handling, and unit testing with JUnit 5 + Mockito.

## Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 3.2, Spring MVC |
| Persistence | Spring Data JPA, Hibernate |
| Database | H2 (in-memory dev) / PostgreSQL (prod) |
| Validation | Jakarta Bean Validation |
| Testing | JUnit 5, Mockito, AssertJ |
| Build | Maven |

## Project Structure

```
src/
├── main/java/com/aashu/taskmanager/
│   ├── TaskManagerApplication.java   # Entry point
│   ├── DataLoader.java               # Sample data on startup
│   ├── controller/
│   │   └── TaskController.java       # REST endpoints
│   ├── service/
│   │   ├── TaskService.java          # Interface
│   │   └── TaskServiceImpl.java      # Business logic
│   ├── repository/
│   │   └── TaskRepository.java       # JPA + custom JPQL queries
│   ├── model/
│   │   └── Task.java                 # Entity (Status, Priority enums)
│   └── exception/
│       ├── TaskNotFoundException.java
│       └── GlobalExceptionHandler.java
└── test/
    └── TaskServiceTest.java          # 7 unit tests (JUnit 5 + Mockito)
```

## Getting Started

**Prerequisites:** Java 17+, Maven 3.8+

```bash
# Clone
git clone https://github.com/<your-username>/task-manager.git
cd task-manager

# Run
mvn spring-boot:run
```

The server starts at `http://localhost:8080`.
H2 console available at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:taskdb`).

## API Reference

### Tasks

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/tasks` | Create a task |
| `GET` | `/api/tasks` | Get all tasks |
| `GET` | `/api/tasks/{id}` | Get task by ID |
| `PUT` | `/api/tasks/{id}` | Full update |
| `PATCH` | `/api/tasks/{id}/status?status=DONE` | Update status only |
| `DELETE` | `/api/tasks/{id}` | Delete task |
| `GET` | `/api/tasks/status/{status}` | Filter by status |
| `GET` | `/api/tasks/priority/{priority}` | Filter by priority |
| `GET` | `/api/tasks/search?keyword=auth` | Search by title |
| `GET` | `/api/tasks/summary` | Count per status |

### Request Body (POST / PUT)

```json
{
  "title": "Implement login page",
  "description": "Build JWT-based login with Spring Security",
  "status": "TODO",
  "priority": "HIGH"
}
```

**Status values:** `TODO` | `IN_PROGRESS` | `DONE`  
**Priority values:** `LOW` | `MEDIUM` | `HIGH`

### Example cURL

```bash
# Create
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Fix auth bug","status":"TODO","priority":"HIGH"}'

# Get all
curl http://localhost:8080/api/tasks

# Update status
curl -X PATCH "http://localhost:8080/api/tasks/1/status?status=IN_PROGRESS"

# Delete
curl -X DELETE http://localhost:8080/api/tasks/1
```

## Running Tests

```bash
mvn test
```

7 unit tests covering: create, read (found + not found), update, delete (found + not found), and list-all.

## Switching to PostgreSQL

1. Add the PostgreSQL driver to `pom.xml`:
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

2. Update `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```
