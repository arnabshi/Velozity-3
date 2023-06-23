package com.TaskManagement.TaskManagementServer.Model;

import com.TaskManagement.TaskManagementServer.Enum.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String dueDate;
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
    @ManyToOne
    @JoinColumn
    Users user;

}
