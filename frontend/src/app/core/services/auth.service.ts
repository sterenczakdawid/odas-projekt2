import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {
  RegisterRequest,
  AuthenticationResponse,
  AuthenticationRequest,
  VerificationRequest,
} from '../interfaces/auth.interface';
import { environment } from '../../../environments/environment.development';
import { ENDPOINTS } from '../constants/api.const';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/v1/auth';
  private KeyStorage = 'token';

  constructor(private http: HttpClient) {}

  register(registerRequest: RegisterRequest) {
    return this.http.post<AuthenticationResponse>(
      `${environment.apiUrl}${ENDPOINTS.REGISTER}`,
      registerRequest
    );
  }

  login(authRequest: AuthenticationRequest) {
    return this.http.post<AuthenticationResponse>(
      `${environment.apiUrl}${ENDPOINTS.LOGIN}`,
      authRequest
    );
  }

  verifyCode(verificationRequest: VerificationRequest) {
    return this.http.post<AuthenticationResponse>(
      `${environment.apiUrl}${ENDPOINTS.VERIFY}`,
      verificationRequest
    );
  }

  get token(): string | null {
    return window.localStorage.getItem(this.KeyStorage) || null;
  }

  set token(val: string) {
    window.localStorage.setItem(this.KeyStorage, val);
  }

  removeToken(): void {
    window.localStorage.removeItem(this.KeyStorage);
  }
}
