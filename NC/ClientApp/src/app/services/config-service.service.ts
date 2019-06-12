import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root"
})
export class ConfigServiceService {
  private javaApiUrl = "http://localhost:8080/api/";
  private restApiUrl = "http://localhost:8080/api/rest/";

  constructor() {}

  public get baseCamundaUrl(): string {
    return this.restApiUrl;
  }
}
