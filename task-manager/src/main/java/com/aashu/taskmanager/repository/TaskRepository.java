package com.aashu.taskmanager.repository;

import com.aashu.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Find all tasks by status
    List<Task> findByStatus(Task.Status status);

    // Find all tasks by priority
    List<Task> findByPriority(Task.Priority priority);

    // Search tasks by title (case-insensitive)
    List<Task> findByTitleContainingIgnoreCase(String keyword);

    // Custom JPQL: tasks matching both status and priority
    @Query("SELECT t FROM Task t WHERE t.status = :status AND t.priority = :priority")
    List<Task> findByStatusAndPriority(@Param("status") Task.Status status,
                                       @Param("priority") Task.Priority priority);

    // Count tasks grouped by status (used for summary endpoint)
    @Query("SELECT t.status, COUNT(t) FROM Task t GROUP BY t.status")
    List<Object[]> countByStatus();
}
