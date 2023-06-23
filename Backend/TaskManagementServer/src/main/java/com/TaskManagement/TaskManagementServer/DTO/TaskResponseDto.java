package com.TaskManagement.TaskManagementServer.DTO;

import com.TaskManagement.TaskManagementServer.Enum.TaskStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TaskResponseDto {
    private int id;
    private TaskStatus taskStatus;
    private String name;
    private String description;
    private String dueDate;
    private String email;

}
