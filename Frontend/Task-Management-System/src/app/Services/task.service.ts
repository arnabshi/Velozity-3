import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseUrl from './urls';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private http: HttpClient) { }
  public getEmail(){
    return JSON.parse(localStorage.getItem('User') || '{}')
  }
  public getTasks(useremail:any){
    return this.http.get(`${baseUrl}/tasks/gettasks?email=${useremail}`)
  }
  public add(task:any){
    return this.http.post(`${baseUrl}/tasks/addtask`,task)
  }
}
