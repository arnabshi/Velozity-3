package com.TaskManagement.TaskManagementServer.DTO;

import com.TaskManagement.TaskManagementServer.Enum.TaskStatus;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TaskResponseDto {
    private UUID id;
    private TaskStatus taskStatus;
    private String name;
    private String description;
    private String dueDate;

}
