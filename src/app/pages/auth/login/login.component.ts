import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import {
  AuthenticationRequest,
  AuthenticationResponse,
  VerificationRequest,
} from '../../../core/interfaces/auth.interface';
import { AuthService } from '../../../core/services/auth.service';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    ReactiveFormsModule,
    MatButtonModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  authResponse: AuthenticationResponse = {};

  loginForm = this.fb.group({
    email: [
      null as string,
      [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(64),
        Validators.pattern(/^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$/),
      ],
    ],
    password: [
      null as string,
      [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(32),
        Validators.pattern(
          /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!^&+=,.()*])(?=\S+$).{8,32}$/
        ),
      ],
    ],
  });

  tfaForm = this.fb.control(null as string, [
    Validators.required,
    Validators.minLength(6),
    Validators.maxLength(6),
    Validators.pattern(/^[0-9]{6}$/),
  ]);

  constructor(
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder
  ) {}

  authenticate() {
    // console.log(this.loginForm.value);
    this.authService
      .login(this.loginForm.value as AuthenticationRequest)
      .subscribe({
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
      email: this.loginForm.get('email').value,
      code: this.tfaForm.value,
    };
    this.authService.verifyCode(verifyRequest).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.accessToken as string);
        this.router.navigate(['home']);
      },
    });
  }
}
