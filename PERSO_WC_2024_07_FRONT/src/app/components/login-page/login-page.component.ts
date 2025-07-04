import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.scss',
})

export class LoginPageComponent {

  constructor(private router: Router){}

  onLogin(){
    this.router.navigateByUrl('home')
  }

}