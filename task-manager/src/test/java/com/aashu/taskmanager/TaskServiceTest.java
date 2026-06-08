package com.aashu.taskmanager;

import com.aashu.taskmanager.exception.TaskNotFoundException;
import com.aashu.taskmanager.model.Task;
import com.aashu.taskmanager.repository.TaskRepository;
import com.aashu.taskmanager.service.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        sampleTask = new Task("Test Task", "A test description",
                Task.Status.TODO, Task.Priority.MEDIUM);
    }

    @Test
    @DisplayName("createTask — saves and returns the new task")
    void createTask_savesAndReturns() {
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        Task result = taskService.createTask(sampleTask);

        assertThat(result.getTitle()).isEqualTo("Test Task");
        verify(taskRepository, times(1)).save(sampleTask);
    }

    @Test
    @DisplayName("getTaskById — returns task when it exists")
    void getTaskById_returnsTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));

        Task result = taskService.getTaskById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Task");
    }

    @Test
    @DisplayName("getTaskById — throws TaskNotFoundException when task is missing")
    void getTaskById_throwsWhenNotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTaskById(99L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("getAllTasks — returns all tasks from repository")
    void getAllTasks_returnsAll() {
        Task t2 = new Task("Another Task", null, Task.Status.DONE, Task.Priority.LOW);
        when(taskRepository.findAll()).thenReturn(List.of(sampleTask, t2));

        List<Task> results = taskService.getAllTasks();

        assertThat(results).hasSize(2);
    }

    @Test
    @DisplayName("deleteTask — calls deleteById when task exists")
    void deleteTask_deletesExistingTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteTask — throws TaskNotFoundException for missing task")
    void deleteTask_throwsWhenNotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.deleteTask(99L))
                .isInstanceOf(TaskNotFoundException.class);
        verify(taskRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("updateTask — updates fields and returns saved task")
    void updateTask_updatesAndReturns() {
        Task update = new Task("Updated Title", "New desc",
                Task.Status.IN_PROGRESS, Task.Priority.HIGH);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        Task result = taskService.updateTask(1L, update);

        assertThat(result.getTitle()).isEqualTo("Updated Title");
        assertThat(result.getStatus()).isEqualTo(Task.Status.IN_PROGRESS);
    }
}
