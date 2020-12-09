import { Component, OnDestroy } from '@angular/core';
import { AuthenticationService } from '../../authentication/authentication.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { TokenService } from '../../authentication/token.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnDestroy {
  public email: string;
  public password: string;
  public error: any = null;

  private subscriptions: Subscription[] = [];

  constructor(private authService: AuthenticationService,
              private tokenService: TokenService,
              private router: Router) { }

  signIn(): void {
    this.subscriptions.push(this.authService.signIn(this.email, this.password).subscribe(() => {
      this.router.navigate(['home']);
    }, error => {
      this.error = error;
    }));
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }
}
