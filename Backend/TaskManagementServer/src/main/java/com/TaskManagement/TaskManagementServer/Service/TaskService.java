package com.TaskManagement.TaskManagementServer.Service;

import com.TaskManagement.TaskManagementServer.DTO.TaskRequestDTO;
import com.TaskManagement.TaskManagementServer.DTO.TaskResponseDto;
import com.TaskManagement.TaskManagementServer.Enum.TaskStatus;
import com.TaskManagement.TaskManagementServer.Model.Task;
import com.TaskManagement.TaskManagementServer.Model.Users;
import com.TaskManagement.TaskManagementServer.Repository.TaskRepository;
import com.TaskManagement.TaskManagementServer.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;
    public TaskResponseDto addTask(TaskRequestDTO taskRequestDTO){
        Users user=userRepository.findByEmail(taskRequestDTO.getEmail());
//        List<Task> taskToBeAdded=new ArrayList<>();
        Task taskToBeAdded=new Task();
        taskToBeAdded.setDescription(taskRequestDTO.getDescription());
        taskToBeAdded.setName(taskRequestDTO.getName());
        taskToBeAdded.setDueDate(taskRequestDTO.getDueDate());
        taskToBeAdded.setUser(user);
        taskToBeAdded.setTaskStatus(TaskStatus.toDo);
//                Task.builder()
//                .description(taskRequestDTO.getDescription())
//                .name(taskRequestDTO.getName())
//                .dueDate(taskRequestDTO.getDueDate())
//                .user(user)
//                .build();
        user.getTasks().add(taskToBeAdded);
        userRepository.save(user);
//        taskRepository.save(taskToBeAdded);
        TaskResponseDto taskResponseDto=TaskResponseDto.builder()
                .id(taskToBeAdded.getId())
                .name(taskToBeAdded.getName())
                .description(taskToBeAdded.getDescription())
                .dueDate(taskRequestDTO.getDueDate())
                .email(taskRequestDTO.getEmail())
                .taskStatus(taskToBeAdded.getTaskStatus())
                .build();
        return taskResponseDto;
        //return 1;
    }
    public List<TaskResponseDto> getTasks(String email){
        Users user=userRepository.findByEmail(email);
        List<TaskResponseDto> allTasks=new ArrayList<>();
        //List<Task> userTasks;
        for(Task task:user.getTasks()){
            TaskResponseDto taskResponseDto=TaskResponseDto.builder()
                    .id(task.getId())
                    .name(task.getName())
                    .description(task.getDescription())
                    .dueDate(task.getDueDate())
                    .email(email)
                    .taskStatus(task.getTaskStatus())
                    .build();
            allTasks.add(taskResponseDto);
        }
        return allTasks;

    }
    public Task updateTask(TaskResponseDto taskResponseDto){
        TaskResponseDto taskResponseDto2=new TaskResponseDto();
        Task task=taskRepository.findById(taskResponseDto.getId()).get();
        task.setName(taskResponseDto.getName());
        task.setDescription(taskResponseDto.getDescription());
        task.setTaskStatus(taskResponseDto.getTaskStatus());
        task.setDueDate(taskResponseDto.getDueDate());
        return taskRepository.save(task);

        //return taskResponseDto;
    }
    public void deleteTask(int id){
         taskRepository.deleteById(id);
    }
}
