import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProcessService {

  private appUrl = 'http://localhost:8081/';

  constructor(private http: HttpClient) { }

  startProcess(processId: String) {
    return this.http.get(this.appUrl + 'process/start/' + processId);
  }

  getNextProcess(taskId: string) {
    return this.http.get(this.appUrl + 'process/getNext');
  }

  getRegisterForm() {
    return this.http.get(this.appUrl + 'process/getRegisterForm');
  }


}
