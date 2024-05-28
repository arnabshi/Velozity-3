package com.TaskManagement.TaskManagementServer.Service;

import com.TaskManagement.TaskManagementServer.Config.JwtUtils;
import com.TaskManagement.TaskManagementServer.DTO.TaskRequestDTO;
import com.TaskManagement.TaskManagementServer.DTO.TaskResponseDto;
import com.TaskManagement.TaskManagementServer.Enum.TaskStatus;
import com.TaskManagement.TaskManagementServer.Model.Task;
import com.TaskManagement.TaskManagementServer.Model.Users;
import com.TaskManagement.TaskManagementServer.Repository.TaskRepository;
import com.TaskManagement.TaskManagementServer.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtUtils jwtUtils;
    public TaskResponseDto addTask(TaskRequestDTO taskRequestDTO,String jwtToken) throws Exception{
        String email=getEmail(jwtToken);
        Users user;
        try{
            user=userRepository.findByEmail(email);
        }
        catch(Exception e){
            throw new Exception("email not found");
        }
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
//        userRepository.save(user);
        taskToBeAdded=taskRepository.save(taskToBeAdded);
        TaskResponseDto taskResponseDto=TaskResponseDto.builder()
                .id(taskToBeAdded.getId())
                .name(taskToBeAdded.getName())
                .description(taskToBeAdded.getDescription())
                .dueDate(taskToBeAdded.getDueDate())
                .taskStatus(taskToBeAdded.getTaskStatus())
                .build();
        return taskResponseDto;
        //return 1;
    }
    public List<TaskResponseDto> getTasks(String jwtToken) throws Exception{
        String userName=getEmail(jwtToken);
//        try {
//            userName=jwtUtils.getUsernameFromToken(jwtToken);
//        }
//        catch (Exception e) {
//            throw new BadCredentialsException(" Token is not valid  !!");
//        }
        Users user=userRepository.findByEmail(userName);
        List<TaskResponseDto> allTasks=new ArrayList<>();
        //List<Task> userTasks;
        for(Task task:user.getTasks()){
            TaskResponseDto taskResponseDto=TaskResponseDto.builder()
                    .id(task.getId())
                    .name(task.getName())
                    .description(task.getDescription())
                    .dueDate(task.getDueDate())
                    .taskStatus(task.getTaskStatus())
                    .build();
            allTasks.add(taskResponseDto);
        }
        return allTasks;

    }
    public TaskResponseDto updateTask(TaskResponseDto taskRequestDto) throws Exception {
        TaskResponseDto taskResponseDto2=new TaskResponseDto();
        Task task=null;
        try{
            task=taskRepository.findById(taskRequestDto.getId()).get();
        }
        catch (Exception e){
            throw new Exception("Task is not present,error :"+ e.getMessage());
        }
        task.setName(taskRequestDto.getName());
        task.setDescription(taskRequestDto.getDescription());
        task.setTaskStatus(taskRequestDto.getTaskStatus());
        task.setDueDate(taskRequestDto.getDueDate());
        Task taskToBeAdded=taskRepository.save(task);
        TaskResponseDto taskResponseDto=TaskResponseDto.builder()
                .id(taskToBeAdded.getId())
                .name(taskToBeAdded.getName())
                .description(taskToBeAdded.getDescription())
                .dueDate(taskToBeAdded.getDueDate())
                .taskStatus(taskToBeAdded.getTaskStatus())
                .build();
        return taskResponseDto;
    }
    public void deleteTask(UUID id) throws Exception {
        try {
            taskRepository.delete(taskRepository.findById(id).get());
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("Task is not exist");
        }catch (Exception e){
            throw new Exception("Not Authorized");
        }

    }
    private String getEmail(String jwtToken){
        try {
            return jwtUtils.getUsernameFromToken(jwtToken);
        }
        catch (Exception e) {
            throw new BadCredentialsException(" Token is not valid  !!");
        }
    }
}
