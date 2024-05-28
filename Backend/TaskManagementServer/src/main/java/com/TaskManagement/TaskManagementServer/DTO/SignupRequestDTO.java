package com.TaskManagement.TaskManagementServer.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignupRequestDTO {
    private String email;
    private String password;
    private String name;
}
