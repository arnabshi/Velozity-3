package com.TaskManagement.TaskManagementServer.Controller;

import com.TaskManagement.TaskManagementServer.DTO.TaskRequestDTO;
import com.TaskManagement.TaskManagementServer.DTO.TaskResponseDto;
import com.TaskManagement.TaskManagementServer.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin("*")
public class TaskController {
    @Autowired
    TaskService taskService;
    @PostMapping("/addtask")
    public ResponseEntity addTask(@RequestBody TaskRequestDTO taskRequestDTO){
        TaskResponseDto taskResponseDto;
        try{
            taskResponseDto=taskService.addTask(taskRequestDTO);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(taskResponseDto,HttpStatus.CREATED);
    }
    @GetMapping("/gettasks")
    public ResponseEntity getTasks(@RequestParam("email") String email){
        List<TaskResponseDto> taskResponseDtoList;
        try{
            taskResponseDtoList=taskService.getTasks(email);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(taskResponseDtoList,HttpStatus.ACCEPTED);
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
