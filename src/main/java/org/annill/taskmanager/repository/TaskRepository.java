package org.annill.taskmanager.repository;

import org.annill.taskmanager.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAuthorId(Long authorId);

    List<Task> findByExecutorId(Long executorId);
}