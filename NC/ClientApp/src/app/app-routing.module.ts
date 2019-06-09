import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { ModuleWithProviders } from "@angular/compiler/src/core";
import { AppComponent } from "./app.component";
import { HomeComponent } from "./components/home/home.component";
import { ErrorPageComponent } from "./components/error-page/error-page.component";
import { SignupComponent } from "./components/auth/signup/signup.component";
import { SigninComponent } from "./components/auth/signin/signin.component";
import { AuthGuard } from "./components/auth/auth-guard.service";
import { MagazinesComponent } from "./components/magazines/magazines.component";
import { FormComponent } from "./camunda/task-form/form.component";
import { TasksComponent } from "./camunda/tasks/tasks.component";

const appRoutes: Routes = [
  { path: "", component: HomeComponent, canActivate: [AuthGuard] },
  { path: "signup", component: SignupComponent },
  { path: "signin", component: SigninComponent },
  {
    path: "magazines",
    component: MagazinesComponent,
    canActivate: [AuthGuard]
  },
  { path: "task-form", component: FormComponent, canActivate: [AuthGuard] },
  { path: "tasks", component: TasksComponent, canActivate: [AuthGuard] },
  {
    path: "not-found",
    component: ErrorPageComponent,
    data: { message: "Page not found!" }
  },
  { path: "**", redirectTo: "/not-found" }
];

@NgModule({
  imports: [
    // RouterModule.forRoot(appRoutes, {useHash: true})
    RouterModule.forRoot(appRoutes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
