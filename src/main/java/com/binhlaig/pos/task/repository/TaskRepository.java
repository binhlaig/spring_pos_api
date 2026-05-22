// src/main/java/com/binhlaig/pos/task/repository/TaskRepository.java
package com.binhlaig.pos.task.repository;

import com.binhlaig.pos.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByShopIdOrderByCreatedAtDesc(Long shopId);

    List<Task> findByShopIdAndAssignedToIsNullOrderByCreatedAtDesc(Long shopId);

    List<Task> findByShopIdAndAssignedToIdOrderByCreatedAtDesc(Long shopId, Long staffId);

    Optional<Task> findByIdAndShopId(Long id, Long shopId);
}