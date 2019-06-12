import { Injectable } from "@angular/core";
import {
  HttpClient,
  HttpEvent,
  HttpHeaders,
  HttpRequest
} from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class UploadService {
  serverUser: any;

  constructor(private http: HttpClient) {}

  pushFileToStorage(file: File, taskId: string): Observable<HttpEvent<{}>> {
    console.log("Ulogovani -> " + this.serverUser);

    const formdata: FormData = new FormData();

    formdata.append("textPDF", file);
    // formdata.append("name", formUpload.name);
    // formdata.append("keywords", "reeciiii");
    // formdata.append("abbstract", formUpload.abbstract);

    let headers = new HttpHeaders();

    headers.append("Content-Type", "multipart/form-data");
    //headers.append('Accept', 'application/json');

    /*
        const req = new HttpRequest('POST', 'http://localhost:8080/upload/post', formdata, {
          reportProgress: true,
          responseType: 'text',
          headers: headers
        });
    */
    const req = new HttpRequest(
      "POST",
      "http://localhost:8080/paper/postUpload/" + taskId,
      formdata,
      {
        reportProgress: true,
        responseType: "text",
        headers: headers
      }
    );
    return this.http.request(req);
  }

  uploadIndex(formdata) {
    return this.http.post("http://localhost:8080/index/add/1", formdata);
  }

  getFiles(): Observable<any> {
    return this.http.get("http://localhost:8080/paper/getallfiles");
  }

  downloadPaper(taskId) {
    //return this.httpClient.get('http://localhost:8080/paper/download/' + paper.name);
    window.location.href = "http://localhost:8080/paper/download/" + taskId;
  }
}
