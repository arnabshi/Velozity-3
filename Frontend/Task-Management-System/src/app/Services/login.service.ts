import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor() { }
  auth(user:any){
    let userArray=[]
    if(localStorage.getItem('Users')){
      userArray=JSON.parse(localStorage.getItem('Users')||'{}')
    }
    return userArray
  }


  addUser(user:any){
    let users:any=[];
    // if(localStorage.getItem('Users'))
    // {
    //   //'https://data-test-http-ng-default-rtdb.firebaseio.com/userData.json'
    //   users=JSON.parse(localStorage.getItem('Users')||'{}');//getting error
    //   users.push(user)
    // }
    // else{
    //  users=[user];
    // }
   localStorage.setItem('Users',JSON.stringify(users));
 }
}
