package com.aashu.taskmanager.service;

import com.aashu.taskmanager.exception.TaskNotFoundException;
import com.aashu.taskmanager.model.Task;
import com.aashu.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    // Constructor injection (preferred over @Autowired field injection)
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task updateTask(Long id, Task updatedTask) {
        Task existing = getTaskById(id);
        existing.setTitle(updatedTask.getTitle());
        existing.setDescription(updatedTask.getDescription());
        existing.setStatus(updatedTask.getStatus());
        existing.setPriority(updatedTask.getPriority());
        return taskRepository.save(existing);
    }

    @Override
    public void deleteTask(Long id) {
        // Verify task exists before deleting
        getTaskById(id);
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getTasksByStatus(Task.Status status) {
        return taskRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getTasksByPriority(Task.Priority priority) {
        return taskRepository.findByPriority(priority);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> searchByTitle(String keyword) {
        return taskRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> getStatusSummary() {
        Map<String, Long> summary = new HashMap<>();
        // Initialize all statuses to 0 so every key is always present
        for (Task.Status s : Task.Status.values()) {
            summary.put(s.name(), 0L);
        }
        for (Object[] row : taskRepository.countByStatus()) {
            summary.put(((Task.Status) row[0]).name(), (Long) row[1]);
        }
        return summary;
    }
}
