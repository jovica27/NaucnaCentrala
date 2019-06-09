import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { ConfigServiceService } from "../../services/config-service.service";

@Injectable()
export class TasksService {
  private url: string;

  constructor(
    private httpClient: HttpClient,
    private configService: ConfigServiceService
  ) {
    this.url = configService.baseCamundaUrl + "task";
  }

  getAll() {
    return this.httpClient.get(this.url + "/getAll");
  }

  getFormFromTask(taskId: string): any {
    return this.httpClient.get(this.url + "/" + taskId);
  }

  executeTask(formValue: any, taskId: string): any {
    return this.httpClient.post(this.url + "/executeTask/" + taskId, formValue);
  }

  executeTaskReviewers(formValue: any, taskId: string): any {
    return this.httpClient.post(
      this.url + "/executeTaskReviewers/" + taskId,
      formValue
    );
  }
}
