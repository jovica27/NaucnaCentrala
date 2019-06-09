import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { ConfigServiceService } from "src/app/services/config-service.service";

@Injectable()
export class MagazinesService {
  url: string;

  constructor(
    private httpClient: HttpClient,
    private configService: ConfigServiceService
  ) {
    this.url = configService.baseCamundaUrl + "magazine";
  }
  getAll() {
    return this.httpClient.get(this.url + "/getAll");
  }

  startPublishProcess(magazineId: string) {
    return this.httpClient.get(this.url + "/" + magazineId);
  }
}
