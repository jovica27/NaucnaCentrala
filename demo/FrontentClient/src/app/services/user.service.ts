import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  loginUser(user, taskId) {
    return this.http.post('http://localhost:8081/user/login/'.concat(taskId), user);
  }

  registerUser(user, taskId) {
    return this.http.post('http://localhost:8081/user/register/'.concat(taskId), user);
  }

}
