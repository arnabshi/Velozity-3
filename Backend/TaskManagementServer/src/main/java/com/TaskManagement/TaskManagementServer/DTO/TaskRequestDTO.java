package com.TaskManagement.TaskManagementServer.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskRequestDTO {
    private String name;
    private String description;
    private String dueDate;
    private String email;
}
