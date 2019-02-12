import { Component, OnInit } from '@angular/core';
import { ProcessService } from 'src/app/services/process-service.service';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-component',
  templateUrl: './login-component.component.html',
  styleUrls: ['./login-component.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private processService: ProcessService,
    private userService: UserService,
    private router: Router) { }

  public formFields: any[];
  private taskId;

  ngOnInit() {
    this.processService.startProcess('Login').subscribe((res: any) => {
      console.log(res);
      this.taskId = res.taskId;
      this.formFields = res.formFields;
    });
  }

  loginUser(value, form) {
    let properties = new Array();
    for (var property in value) {
      properties.push({ fieldId: property, fieldValue: value[property] });
    }

    this.userService.loginUser(properties, this.taskId).subscribe(
      (res: any) => {
        console.log(res);
        this.taskId = res.taskId;
        this.processService.getNextProcess(this.taskId).subscribe(
          (res: any) => {
          }
        );
      }
    );
  }

}
