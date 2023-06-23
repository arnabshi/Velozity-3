import { Component } from '@angular/core';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {

  constructor(private userService:UserService){}
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
        alert('success');
      },
      (error)=>{
        console.log(error);
        alert('failed');
      }
      )
  }
}
