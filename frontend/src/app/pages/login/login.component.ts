import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import {
  AuthenticationRequest,
  AuthenticationResponse,
  VerificationRequest,
} from '../../core/interfaces/auth.interface';
import { AuthService } from '../../core/services/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  authRequest: AuthenticationRequest = {};
  otpCode = '';
  authResponse: AuthenticationResponse = {};

  constructor(private authService: AuthService, private router: Router) {}

  authenticate() {
    this.authService.login(this.authRequest).subscribe({
      next: (response) => {
        this.authResponse = response;
        if (!this.authResponse.mfaEnabled) {
          localStorage.setItem('token', response.accessToken as string);
          this.router.navigate(['home']);
        }
      },
    });
  }

  verifyCode() {
    const verifyRequest: VerificationRequest = {
      email: this.authRequest.email,
      code: this.otpCode,
    };
    this.authService.verifyCode(verifyRequest).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.accessToken as string);
        this.router.navigate(['home']);
      },
    });
  }
}
