package com.TaskManagement.TaskManagementServer.Repository;

import com.TaskManagement.TaskManagementServer.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task,UUID> {
//    Task findByUUID(UUID id);
//    Task deleteByUUID(UUID id);
}
