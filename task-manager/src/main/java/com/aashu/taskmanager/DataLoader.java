package com.aashu.taskmanager;

import com.aashu.taskmanager.model.Task;
import com.aashu.taskmanager.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadSampleData(TaskRepository repo) {
        return args -> {
            repo.save(new Task("Set up CI/CD pipeline",
                    "Configure GitHub Actions for automated builds and tests",
                    Task.Status.TODO, Task.Priority.HIGH));

            repo.save(new Task("Implement user authentication",
                    "Add JWT-based auth with Spring Security",
                    Task.Status.IN_PROGRESS, Task.Priority.HIGH));

            repo.save(new Task("Write unit tests for service layer",
                    "Achieve 80%+ coverage using JUnit 5 and Mockito",
                    Task.Status.TODO, Task.Priority.MEDIUM));

            repo.save(new Task("Add Swagger/OpenAPI docs",
                    "Document all REST endpoints with springdoc-openapi",
                    Task.Status.DONE, Task.Priority.LOW));

            repo.save(new Task("Migrate to PostgreSQL",
                    "Replace H2 with a persistent PostgreSQL instance for production",
                    Task.Status.TODO, Task.Priority.MEDIUM));
        };
    }
}
