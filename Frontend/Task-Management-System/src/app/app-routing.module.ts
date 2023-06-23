import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignUpComponent } from './Pages/sign-up/sign-up.component';
import { LogInComponent } from './Pages/log-in/log-in.component';
import { FooterComponent } from './Components/footer/footer.component';
import { TaskComponent } from './Pages/task/task.component';
import { AddtaskComponent } from './Pages/addtask/addtask.component';

const routes: Routes = [
  {
    path:'',
    component:TaskComponent,
    pathMatch:"full"
  },
  {
    path:'signup',
    component:SignUpComponent
  },
  {
    path:'login',
    component:LogInComponent
  },
  {
    path:'footer',
    component:FooterComponent
  },
  {
    path:'task',
    component:TaskComponent
  },
  {
    path:'addtask',
    component:AddtaskComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
