# Task Manager API

RESTful task management API built with Spring Boot 3, Spring Data JPA, and JUnit 5.

## Tech Stack
Java 17 · Spring Boot · Spring MVC · Spring Data JPA · H2 · Maven

## Run Locally

**Prerequisites:** Java 17+, Maven 3.8+

```bash
git clone https://github.com/Aashu-Github/task-manager-api.git
cd task-manager-api
mvn spring-boot:run
```

Server starts at `http://localhost:8080`

## Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/tasks` | Create a task |
| GET | `/api/tasks` | Get all tasks |
| GET | `/api/tasks/{id}` | Get task by ID |
| PUT | `/api/tasks/{id}` | Update a task |
| DELETE | `/api/tasks/{id}` | Delete a task |
| GET | `/api/tasks/status/{status}` | Filter by status |
| GET | `/api/tasks/priority/{priority}` | Filter by priority |
| GET | `/api/tasks/search?keyword=` | Search by title |

## Example Request

```json
POST /api/tasks
{
  "title": "Fix auth bug",
  "description": "JWT token not refreshing correctly",
  "status": "TODO",
  "priority": "HIGH"
}
```

**Status:** `TODO` `IN_PROGRESS` `DONE`  
**Priority:** `LOW` `MEDIUM` `HIGH`

## Tests

```bash
mvn test
```
