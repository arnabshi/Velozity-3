import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TaskService } from 'src/app/Services/task.service';

@Component({
  selector: 'app-addtask',
  templateUrl: './addtask.component.html',
  styleUrls: ['./addtask.component.css']
})
export class AddtaskComponent {
  public task={
    name:'',
    description:'',
    dueDate:'',
    email:''
  }

  constructor(private taskService:TaskService,private router:Router){

  }

  formSubmit(){
    this.task.email=this.taskService.getEmail().email;
    console.log(this.task)
    this.taskService.add(this.task).subscribe(
      (data)=>{
        console.log(data);
        alert('Task added Succeessfully');
        this.router.navigate(['task'])
      },
      (error)=>{
        console.log(error);
        alert('task adding failed');
      }
    )
  }

}
