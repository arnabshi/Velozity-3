package com.TaskManagement.TaskManagementServer.Controller;

import com.TaskManagement.TaskManagementServer.DTO.UserLogInDTO;
import com.TaskManagement.TaskManagementServer.Model.Users;
import com.TaskManagement.TaskManagementServer.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
//@CrossOrigin("*")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody Users user){
        //userService.addSignUpUser(user);
        return new ResponseEntity(userService.addSignUpUser(user),HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public  ResponseEntity logIn(@RequestBody UserLogInDTO userLoginDTO){
        return new ResponseEntity<>(userService.checkLogIn(userLoginDTO),HttpStatus.ACCEPTED);
    }
}
