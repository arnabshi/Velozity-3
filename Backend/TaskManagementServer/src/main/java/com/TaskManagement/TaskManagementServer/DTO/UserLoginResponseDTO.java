package com.TaskManagement.TaskManagementServer.DTO;

import com.TaskManagement.TaskManagementServer.Model.Task;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginResponseDTO {
    private int id;
    private String email;
    private String password;
    private String name;
    List<TaskResponseDto> tasks=new ArrayList<>();
}
