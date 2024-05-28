package com.TaskManagement.TaskManagementServer.Service;

import com.TaskManagement.TaskManagementServer.Config.JwtUtils;
import com.TaskManagement.TaskManagementServer.DTO.SignupRequestDTO;
import com.TaskManagement.TaskManagementServer.DTO.TaskResponseDto;
import com.TaskManagement.TaskManagementServer.DTO.UserLogInDTO;
import com.TaskManagement.TaskManagementServer.DTO.UserLoginResponseDTO;
import com.TaskManagement.TaskManagementServer.Model.Task;
import com.TaskManagement.TaskManagementServer.Model.Users;
import com.TaskManagement.TaskManagementServer.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AuthenticationManager authenticationManager;
    public String addSignUpUser (SignupRequestDTO user) throws Exception{
        var encodedPassword=passwordEncoder.encode(user.getPassword());
        ArrayList<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        //Users u;
        Users users=Users.builder()
                .email(user.getEmail())
                .password(encodedPassword)
                .name(user.getName())
                .authorities(authorities)
                .build();
        try{
            userRepository.save(users);
        }
        catch (Exception e){
           throw new Exception("Email: " + user.getEmail() +" already exist");
        }
        return jwtUtils.generateToken(users.getEmail());
    }
    public UserLoginResponseDTO checkLogIn(UserLogInDTO logInDetails) throws Exception{
        var authToken=new UsernamePasswordAuthenticationToken(logInDetails.getEmail(),logInDetails.getPassword());
        String jwtToken=null;
        try{
            var authenticate= authenticationManager.authenticate(authToken);
            System.out.println(authenticate);
            String userName=((UserDetails)(authenticate.getPrincipal())).getUsername();
            jwtToken=jwtUtils.generateToken(userName);
            Users user=userRepository.findByEmail(logInDetails.getEmail());
            List<TaskResponseDto> userTasks=new ArrayList<>();
            for (Task task : user.getTasks()) {
                TaskResponseDto taskResponseDto = TaskResponseDto.builder()
                        .id(task.getId())
                        .name(task.getName())
                        .description(task.getDescription())
                        .dueDate(task.getDueDate())
                        .taskStatus(task.getTaskStatus())
                        .build();
                userTasks.add(taskResponseDto);

            }
            UserLoginResponseDTO userLoginResponseDTO = UserLoginResponseDTO.builder()
                    .jwtToken(jwtToken)
                    .email(user.getEmail())
                    .name(user.getName())
                    .tasks(userTasks)
                    .build();
            return userLoginResponseDTO;
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }

    public String verifyToken(String jwtToken) {
        try {
            String userName=jwtUtils.getUsernameFromToken(jwtToken);
            return userName;
        }
        catch (Exception e) {
            throw new BadCredentialsException(" Token is not valid  !!");
        }
    }
}
