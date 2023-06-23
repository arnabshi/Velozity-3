package com.TaskManagement.TaskManagementServer.Controller;

import com.TaskManagement.TaskManagementServer.DTO.UserLogInDTO;
import com.TaskManagement.TaskManagementServer.DTO.UserLoginResponseDTO;
import com.TaskManagement.TaskManagementServer.Model.Users;
import com.TaskManagement.TaskManagementServer.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
//@CrossOrigin("*")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody Users user){
        //userService.addSignUpUser(user);
        Users users;
        try {
            users=userService.addSignUpUser(user);
        }
        catch(Exception e){
           return new ResponseEntity(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(users,HttpStatus.CREATED);
    }
    //@CrossOrigin(origins = "http://localhost:8080/user/login")
    @PostMapping("/login")
    public  ResponseEntity logIn(@RequestBody UserLogInDTO userLoginDTO){
        UserLoginResponseDTO userLoginResponseDTO;
        try{
            userLoginResponseDTO=userService.checkLogIn(userLoginDTO);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userLoginResponseDTO,HttpStatus.ACCEPTED);
    }
}
