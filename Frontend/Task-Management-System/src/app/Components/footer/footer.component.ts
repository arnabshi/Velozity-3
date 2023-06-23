import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit{

  dataForm:any
  token:any
  constructor(private router:Router) { }

  ngOnInit(): void {
    this.dataForm=new FormGroup({
      id:new FormControl(null,[Validators.required,Validators.minLength(6),Validators.pattern('^[0-9]+$'),Validators.maxLength(6)]),
      password:new FormControl(null,[Validators.required,Validators.minLength(6)])
    });
  }
  onLogin(){
  //  this.data= JSON.stringify(this.dataFrom.value);
    this.token= "sample"
    //this.authService.auth(this.dataForm.value)
    //console.log(this.token);
    
    if(this.token){
      localStorage.setItem('Token',JSON.stringify(this.token))
      //this.router.navigate(['dashboard'])
    }
    else{
      alert("invalid mail or password")
      
    }
    
  }
}
