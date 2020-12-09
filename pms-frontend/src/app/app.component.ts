import { Component } from '@angular/core';
import { AuthenticationService } from './authentication/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'pms-frontend';

  constructor(public authService: AuthenticationService,
              private router: Router) { }

  signOut(): void {
    this.authService.signOut();
    this.router.navigate(['home']);
  }
}
