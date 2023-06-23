import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.css']
})
export class LogInComponent {
  constructor(private userService:UserService,private router:Router){}
  public user={
    email:'',
    password:''
  }
  formLogin(){
   // console.log(this.user);

    this.userService.checkLogin(this.user).subscribe(
      (data)=>{
        console.log(data);
        localStorage.setItem('User',JSON.stringify(data));
        alert('success');
        this.router.navigate(['task']);
      },
      (error)=>{
        console.log(error);
        alert('failed');
      }
      )
  }

}
