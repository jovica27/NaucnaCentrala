import { Component, OnInit } from "@angular/core";
import { MagazinesService } from "./magazines.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-magazines",
  templateUrl: "./magazines.component.html",
  styleUrls: ["./magazines.component.css"]
})
export class MagazinesComponent implements OnInit {
  magazines: any;
  accordionMap = new Map<string, boolean>();

  constructor(
    private magazinesService: MagazinesService,
    private router: Router
  ) {}

  ngOnInit() {
    this.magazinesService.getAll().subscribe(
      data => {
        this.magazines = data;
        console.log(this.magazines);
        this.magazines.forEach(element => {
          this.accordionMap[element.name] = false;
        });
      },
      error => console.log(error)
    );
  }

  publishPaper(magazineId: string) {
    this.magazinesService.startPublishProcess(magazineId).subscribe(data => {
      console.log(data);
      this.router.navigate(["/tasks"]);
    });
  }
}
