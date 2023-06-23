package com.TaskManagement.TaskManagementServer.Service;

import com.TaskManagement.TaskManagementServer.DTO.TaskResponseDto;
import com.TaskManagement.TaskManagementServer.DTO.UserLogInDTO;
import com.TaskManagement.TaskManagementServer.DTO.UserLoginResponseDTO;
import com.TaskManagement.TaskManagementServer.Model.Task;
import com.TaskManagement.TaskManagementServer.Model.Users;
import com.TaskManagement.TaskManagementServer.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public Users addSignUpUser (Users user) throws Exception{
        Users users=new Users();
        try{
            users=userRepository.save(user);
        }
        catch (Exception e){
           throw new Exception("Email exist");
        }
        return users;
    }
    public UserLoginResponseDTO checkLogIn(UserLogInDTO logInDetails) throws Exception{
        Users user=userRepository.findByEmail(logInDetails.getEmail());
        List<TaskResponseDto> userTasks=new ArrayList<>();
        if(user.getPassword().equals(logInDetails.getPassword())) {
            for (Task task : user.getTasks()) {
                TaskResponseDto taskResponseDto = TaskResponseDto.builder()
                        .id(task.getId())
                        .name(task.getName())
                        .description(task.getDescription())
                        .dueDate(task.getDueDate())
                        .email(logInDetails.getEmail())
                        .taskStatus(task.getTaskStatus())
                        .build();
                userTasks.add(taskResponseDto);
            }
            UserLoginResponseDTO userLoginResponseDTO = UserLoginResponseDTO.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .name(user.getName())
                    .tasks(userTasks)
                    .build();
            return userLoginResponseDTO;
        }
        else
           throw new Exception("Login Failed");
    }
}
