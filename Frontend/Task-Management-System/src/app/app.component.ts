import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Task-Management-System';
  //title = 'forms';
  logged(){
    return localStorage.getItem('User')
  }
  onLogOut(){
    localStorage.removeItem('User')
  }
}
