import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { HeaderComponent } from "./components/header/header.component";
import { HomeComponent } from "./components/home/home.component";
import { ErrorPageComponent } from "./components/error-page/error-page.component";
import { SigninComponent } from "./components/auth/signin/signin.component";
import { SignupComponent } from "./components/auth/signup/signup.component";
import { AuthGuard } from "./components/auth/auth-guard.service";
import { AuthService } from "./components/auth/auth.service";
import { MagazinesComponent } from "./components/magazines/magazines.component";
import { MagazinesService } from "./components/magazines/magazines.service";
import { AuthInterceptor } from "./components/auth/auth.interceptor";
import { TasksComponent } from "./camunda/tasks/tasks.component";
import { FormComponent } from "./camunda/task-form/form.component";
import { TasksService } from "./camunda/tasks/tasks.service";
import { ConfigServiceService } from "./services/config-service.service";
import { UploadService } from "./services/upload.service";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    ErrorPageComponent,
    SigninComponent,
    SignupComponent,
    MagazinesComponent,
    TasksComponent,
    FormComponent
  ],
  imports: [BrowserModule, FormsModule, HttpClientModule, AppRoutingModule],
  providers: [
    MagazinesService,
    TasksService,
    AuthService,
    AuthGuard,
    ConfigServiceService,
    UploadService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
