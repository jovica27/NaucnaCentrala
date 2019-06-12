import { Component, OnInit } from "@angular/core";
import { TasksService } from "../tasks/tasks.service";
import { Router } from "@angular/router";
import { UploadService } from "../../services/upload.service";
import { HttpResponse, HttpEventType } from "@angular/common/http";

@Component({
  selector: "app-form",
  templateUrl: "./form.component.html",
  styleUrls: ["./form.component.css"]
})
export class FormComponent implements OnInit {
  form: any;
  taskId: any;
  reviewerForm: boolean = false;
  public isChecked = true;
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };
  availableReviewers = [];
  selectedReviewers = [];

  constructor(
    private taskService: TasksService,
    private router: Router,
    private uploadService: UploadService
  ) {}

  ngOnInit() {
    var retrievedObject = localStorage.getItem("form");
    this.form = JSON.parse(retrievedObject);
    if (this.form[0].id === "reviewer") {
      this.form.forEach(element => {
        if (element.type.name === "reviewertype") {
          this.availableReviewers.push(element);
        }
      });
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
    console.log(this.selectedReviewers);
    let body = { reviewers: this.selectedReviewers };
    this.taskService
      .executeTaskReviewers(body, this.taskId)
      .subscribe((res: any) => {
        this.router.navigate(["/tasks"]);
      });
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  upload(formUpload) {
    this.progress.percentage = 0;

    this.currentFileUpload = this.selectedFiles.item(0);

    this.uploadService
      .pushFileToStorage(this.currentFileUpload, this.taskId)
      .subscribe(event => {
        if (event.type === HttpEventType.UploadProgress) {
          this.progress.percentage = Math.round(
            (100 * event.loaded) / event.total
          );
        } else if (event instanceof HttpResponse) {
          console.log("File is completely uploaded!");
        }
      });

    this.selectedFiles = undefined;
  }

  public download() {
    this.uploadService.downloadPaper(this.taskId);
  }
}
