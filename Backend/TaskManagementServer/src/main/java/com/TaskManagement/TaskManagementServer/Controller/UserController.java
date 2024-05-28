package com.TaskManagement.TaskManagementServer.Controller;

import com.TaskManagement.TaskManagementServer.DTO.SignupRequestDTO;
import com.TaskManagement.TaskManagementServer.DTO.UserLogInDTO;
import com.TaskManagement.TaskManagementServer.DTO.UserLoginResponseDTO;
import com.TaskManagement.TaskManagementServer.Model.Users;
import com.TaskManagement.TaskManagementServer.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
//@CrossOrigin("*")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody SignupRequestDTO user){
        //userService.addSignUpUser(user);
        String jwtToken;
        try {
            jwtToken=userService.addSignUpUser(user);
        }
        catch(Exception e){
           return new ResponseEntity(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(jwtToken,HttpStatus.CREATED);
    }
    //@CrossOrigin(origins = "http://localhost:8080/user/login")
    @PostMapping("/login")
    public  ResponseEntity logIn(@RequestBody UserLogInDTO userLoginDTO){
        UserLoginResponseDTO userLoginResponseDTO;
        try{
            userLoginResponseDTO=userService.checkLogIn(userLoginDTO);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userLoginResponseDTO,HttpStatus.OK);
    }
    @PostMapping("/verify-token")
    public ResponseEntity<String> verifyToken(@RequestHeader("Authorization") String jwtToken) {
        try {
            var username = userService.verifyToken(jwtToken.substring(7));

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(username);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }
}
