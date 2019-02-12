import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing-module';
import { LoginComponent } from './components/login-component/login-component.component';
import { ProcessService } from './services/process-service.service';
import { HttpClientModule } from '@angular/common/http';
import { UserService } from './services/user.service';
import { RegisterComponent } from './components/register/register.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SearchComponent } from './components/search/search.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    SearchComponent
  ],
  imports: [
    BrowserModule,
    NgbModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [ProcessService, UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
