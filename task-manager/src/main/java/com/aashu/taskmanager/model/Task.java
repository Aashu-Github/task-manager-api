package com.aashu.taskmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be 100 characters or fewer")
    @Column(nullable = false)
    private String title;

    @Size(max = 500, message = "Description must be 500 characters or fewer")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum Status {
        TODO, IN_PROGRESS, DONE
    }

    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null)   status   = Status.TODO;
        if (priority == null) priority = Priority.MEDIUM;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ── Constructors ────────────────────────────────────────────────────────
    public Task() {}

    public Task(String title, String description, Status status, Priority priority) {
        this.title       = title;
        this.description = description;
        this.status      = status;
        this.priority    = priority;
    }

    // ── Getters & Setters ───────────────────────────────────────────────────
    public Long getId()                  { return id; }
    public String getTitle()             { return title; }
    public void setTitle(String title)   { this.title = title; }
    public String getDescription()             { return description; }
    public void setDescription(String d)       { this.description = d; }
    public Status getStatus()                  { return status; }
    public void setStatus(Status status)       { this.status = status; }
    public Priority getPriority()              { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public LocalDateTime getCreatedAt()        { return createdAt; }
    public LocalDateTime getUpdatedAt()        { return updatedAt; }
}
