package com.TaskManagement.TaskManagementServer.Controller;

import com.TaskManagement.TaskManagementServer.DTO.TaskRequestDTO;
import com.TaskManagement.TaskManagementServer.DTO.TaskResponseDto;
import com.TaskManagement.TaskManagementServer.Model.Task;
import com.TaskManagement.TaskManagementServer.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
//@CrossOrigin("*")
public class TaskController {
    @Autowired
    TaskService taskService;
    @PostMapping("/addtask")
    public ResponseEntity addTask(@RequestBody TaskRequestDTO taskRequestDTO,@RequestHeader("Authorization") String jwtToken){
        TaskResponseDto taskResponseDto;
        try{
            taskResponseDto=taskService.addTask(taskRequestDTO,jwtToken.substring(7));
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(taskResponseDto,HttpStatus.CREATED);
    }
    @GetMapping("/gettasks")
    public ResponseEntity getTasks(@RequestHeader("Authorization") String jwtToken){
        List<TaskResponseDto> taskResponseDtoList;
        try{
            taskResponseDtoList=taskService.getTasks(jwtToken.substring(7));
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(taskResponseDtoList,HttpStatus.OK);
    }
    @PutMapping("/updatetask")
    public ResponseEntity updateTask(@RequestBody TaskResponseDto taskRequestDto){
        TaskResponseDto task;
        try {
            task=taskService.updateTask(taskRequestDto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(task,HttpStatus.CREATED);
    }
    @DeleteMapping("/deletetask")
    public ResponseEntity deleteTask(@RequestParam("id") UUID id){
        try {
            taskService.deleteTask(id);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
