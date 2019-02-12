import { Component, OnInit } from '@angular/core';
import { ProcessService } from 'src/app/services/process-service.service';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  public successMessage = "";

  constructor(private processService: ProcessService,
    private userService: UserService,
    private router: Router) { }

  private taskId;
  public formFields;

  ngOnInit() {
    this.processService.startProcess('Registracija').subscribe((res: any) => {
      console.log(res);
      this.taskId = res.taskId;
      this.formFields = res.formFields;
      console.log(this.formFields[0].id)
    });

    // this.processService.getRegisterForm().subscribe(
    //   (res: any) => {
    //     console.log(res);
    //     this.taskId = res.taskId;
    //     this.formFields = res.formFields;
    //     }
    //   );

  }

  registerUser(value, form) {
    let properties = new Array();
    for (var property in value) {
      properties.push({ fieldId: property, fieldValue: value[property] });
    }

    this.userService.registerUser(properties, this.taskId).subscribe(
      (res: any) => {
        console.log(res);
        this.successMessage = "Registered!";
        // this.taskId = res.taskId;
        // this.processService.getNextProcess(this.taskId).subscribe(
        //   (res: any) => {
        //     this.router.navigateByUrl('/login');
        //   }
        // );
      }
    );

  }

}
