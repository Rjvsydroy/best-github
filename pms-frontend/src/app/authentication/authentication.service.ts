import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { TokenService } from './token.service';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  constructor(private router: Router,
              private http: HttpClient,
              private tokenService: TokenService) {}

  signIn(emailAddress: string, password: string): Observable<any> {
    return this.http.post('http://localhost:8080/auth/sign-in', {
      emailAddress,
      password
    }).pipe(
      map((response: any) => {
        this.tokenService.saveToken(response.token);
        this.tokenService.saveUsername(response.emailAddress);
        this.tokenService.saveUserRole(response.role);
      })
    );
  }

  signOut(): void {
    this.tokenService.signOut();
  }

  isLoggedIn(): boolean {
    return this.tokenService.getToken() !== null;
  }
}
