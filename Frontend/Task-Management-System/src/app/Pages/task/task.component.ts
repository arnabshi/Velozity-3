import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TaskService } from 'src/app/Services/task.service';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit{

  Tasks:any=[]
  getUser:any=this.taskService.getEmail()
  user:any={}

  constructor(private taskService:TaskService, private router:Router){
    
  }
  
  ngOnInit(): void {
    this.getTasks();
  }

  getTasks(){
    
    this.taskService.getTasks(this.getUser.email).subscribe(
      (data:any)=>{
        // data.array.forEach((element:any) => {
        //   console.log(element.id)
        // });
        this.Tasks=data;
        console.log(this.Tasks)
      },
      (error)=>{
        console.log(error)
      }
    )
  }
  addTask(){
    this.router.navigate(['addtask']);
  }
   deleteItem(id:any){
    console.log(id)
    this.taskService.delete(id).subscribe(
      (data:any)=>{
        console.log(data)
        this.getTasks();
        alert("Deleted Successfully")
      },
      (error)=>{
        console.log(error)
        alert("Deletion failed")
      }
    )
   }

}
