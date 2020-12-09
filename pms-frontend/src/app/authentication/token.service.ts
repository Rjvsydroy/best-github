import { Injectable } from '@angular/core';

export const TOKEN = 'token';
export const USERNAME = 'username';
export const USER_ROLE = 'role';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  constructor() { }

  signOut(): void {
    window.sessionStorage.clear();
  }

  saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN);
    window.sessionStorage.setItem(TOKEN, token);
  }

  getToken(): string {
    return window.sessionStorage.getItem(TOKEN);
  }

  saveUsername(token: string): void {
    window.sessionStorage.removeItem(USERNAME);
    window.sessionStorage.setItem(USERNAME, token);
  }

  getUsername(): string {
    return window.sessionStorage.getItem(USERNAME);
  }

  saveUserRole(token: string): void {
    window.sessionStorage.removeItem(USER_ROLE);
    window.sessionStorage.setItem(USER_ROLE, token);
  }

  getUserRole(): string {
    return window.sessionStorage.getItem(USER_ROLE);
  }
}
