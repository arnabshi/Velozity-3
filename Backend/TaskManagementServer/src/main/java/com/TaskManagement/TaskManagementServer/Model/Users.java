package com.TaskManagement.TaskManagementServer.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private List<GrantedAuthority> authorities;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Task> tasks=new ArrayList<>();
}
