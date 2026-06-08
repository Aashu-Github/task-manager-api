package com.aashu.taskmanager.controller;

import com.aashu.taskmanager.model.Task;
import com.aashu.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // ── CREATE ───────────────────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task created = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ── READ (all) ────────────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // ── READ (one) ───────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    // ── UPDATE (full) ────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,
                                           @Valid @RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }

    // ── UPDATE (status only — partial update) ────────────────────────────────
    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateStatus(@PathVariable Long id,
                                             @RequestParam Task.Status status) {
        Task existing = taskService.getTaskById(id);
        existing.setStatus(status);
        return ResponseEntity.ok(taskService.updateTask(id, existing));
    }

    // ── DELETE ───────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // ── FILTER by status ─────────────────────────────────────────────────────
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getByStatus(@PathVariable Task.Status status) {
        return ResponseEntity.ok(taskService.getTasksByStatus(status));
    }

    // ── FILTER by priority ───────────────────────────────────────────────────
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getByPriority(@PathVariable Task.Priority priority) {
        return ResponseEntity.ok(taskService.getTasksByPriority(priority));
    }

    // ── SEARCH by title ──────────────────────────────────────────────────────
    @GetMapping("/search")
    public ResponseEntity<List<Task>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(taskService.searchByTitle(keyword));
    }

    // ── SUMMARY (count per status) ────────────────────────────────────────────
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Long>> getSummary() {
        return ResponseEntity.ok(taskService.getStatusSummary());
    }
}
