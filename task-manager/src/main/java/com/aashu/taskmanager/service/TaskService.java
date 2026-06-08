package com.aashu.taskmanager.service;

import com.aashu.taskmanager.model.Task;

import java.util.List;
import java.util.Map;

public interface TaskService {
    Task         createTask(Task task);
    Task         getTaskById(Long id);
    List<Task>   getAllTasks();
    Task         updateTask(Long id, Task updatedTask);
    void         deleteTask(Long id);
    List<Task>   getTasksByStatus(Task.Status status);
    List<Task>   getTasksByPriority(Task.Priority priority);
    List<Task>   searchByTitle(String keyword);
    Map<String, Long> getStatusSummary();
}
