import { Routes } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { AllegianceListComponent } from './components/allegiance-list/allegiance-list.component';
import { MapsComponent } from './components/maps/maps.component';

export const routes: Routes = [
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: "home", component: HomepageComponent},
    { path: "login", component: LoginPageComponent},
    { path: "clans", component: AllegianceListComponent},
    { path: "maps", component: MapsComponent},
    { path: "life", component: AllegianceListComponent},
    { path: "bonuses", component: LoginPageComponent}
];
