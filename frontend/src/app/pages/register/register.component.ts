import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import {
  RegisterRequest,
  AuthenticationResponse,
  VerificationRequest,
} from '../../core/interfaces/auth.interface';
import { AuthService } from '../../core/services/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent {
  registerRequest: RegisterRequest = {};
  authResponse: AuthenticationResponse = {};
  message = '';
  otpCode = '';

  constructor(private authService: AuthService, private router: Router) {}

  registerUser() {
    this.message = '';
    this.authService.register(this.registerRequest).subscribe({
      next: (response) => {
        if (response) {
          this.authResponse = response;
        } else {
          // inform the user
          this.message =
            'Account created successfully\nYou will be redirected to the Login page in 3 seconds';
          setTimeout(() => {
            this.router.navigate(['login']);
          }, 3000);
        }
      },
    });
  }

  verifyTfa() {
    this.message = '';
    const verifyRequest: VerificationRequest = {
      email: this.registerRequest.email,
      code: this.otpCode,
    };
    this.authService.verifyCode(verifyRequest).subscribe({
      next: (response) => {
        this.message =
          'Account created successfully\nYou will be redirected to the Welcome page in 3 seconds';
        setTimeout(() => {
          localStorage.setItem('token', response.accessToken as string);
          this.router.navigate(['home']);
        }, 3000);
      },
    });
  }
}
