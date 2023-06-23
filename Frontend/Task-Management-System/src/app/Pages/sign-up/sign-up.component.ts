import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {

  constructor(private userService:UserService,private router:Router){}
  public user={
    name:'',
    email:'',
    password:''
  }

  formSubmit(){
    console.log(this.user);
    this.userService.addUser(this.user).subscribe(
      (data)=>{
        console.log(data);
        this.user={
          name:'',
          email:'',
          password:''
        }
        alert('Sign up success, please log in');
        this.router.navigate(['login'])
      },
      (error)=>{
        console.log(error);
        alert('sign up failed');
        this.user.password=''
      }
      )
  }
}
