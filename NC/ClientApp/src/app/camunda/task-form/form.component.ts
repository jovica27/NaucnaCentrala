import { Component, OnInit } from "@angular/core";
import { TasksService } from "../tasks/tasks.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-form",
  templateUrl: "./form.component.html",
  styleUrls: ["./form.component.css"]
})
export class FormComponent implements OnInit {
  form: any;
  taskId: any;
  reviewerForm: boolean = false;
  public reviewerIds;
  public isChecked = true;
  constructor(private taskService: TasksService, private router: Router) {}

  ngOnInit() {
    var retrievedObject = localStorage.getItem("form");
    this.form = JSON.parse(retrievedObject);
    if (this.form[0].id === "reviewer") {
      this.reviewerForm = true;
    }
    this.taskId = localStorage.getItem("taskId");
  }

  submitForm(formValue, f) {
    this.taskService.executeTask(formValue, this.taskId).subscribe(data => {
      this.router.navigate(["/tasks"]);
    });
  }

  submitReviewers() {
    let body = { reviewers: this.reviewerIds.split(",") };
    this.taskService
      .executeTaskReviewers(body, this.taskId)
      .subscribe((res: any) => {
        this.router.navigate(["/tasks"]);
      });
  }
}
