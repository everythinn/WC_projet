import { Routes } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { LoginPageComponent } from './components/login-page/login-page.component';

export const routes: Routes = [
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: "home", component: HomepageComponent},
    { path: "login", component: LoginPageComponent}
];
