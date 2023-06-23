package com.TaskManagement.TaskManagementServer.Controller;

import com.TaskManagement.TaskManagementServer.DTO.TaskRequestDTO;
import com.TaskManagement.TaskManagementServer.DTO.TaskResponseDto;
import com.TaskManagement.TaskManagementServer.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@CrossOrigin("*")
public class TaskController {
    @Autowired
    TaskService taskService;
    @PostMapping("/addtask")
    public ResponseEntity addTask(@RequestBody TaskRequestDTO taskRequestDTO){
        return new ResponseEntity<>(taskService.addTask(taskRequestDTO),HttpStatus.CREATED);
    }
    @GetMapping("/gettasks")
    public ResponseEntity getTasks(@RequestParam("email") String email){
        return new ResponseEntity<>(taskService.getTasks(email),HttpStatus.ACCEPTED);
    }
    @PutMapping("/updatetask")
    public ResponseEntity updateTask(@RequestBody TaskResponseDto taskResponseDto){
        return new ResponseEntity<>(taskService.updateTask(taskResponseDto),HttpStatus.CREATED);
    }
    @DeleteMapping("/deletetask")
    public ResponseEntity deleteTask(@RequestParam("id") int id){
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
