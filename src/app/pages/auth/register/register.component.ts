import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import {
  RegisterRequest,
  AuthenticationResponse,
  VerificationRequest,
} from '../../../core/interfaces/auth.interface';
import { AuthService } from '../../../core/services/auth.service';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { Observable, Subject, map } from 'rxjs';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, ReactiveFormsModule, MatButtonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent implements OnInit, OnDestroy {
  authResponse: AuthenticationResponse = {};
  message = '';
  entropy$: Observable<number>;
  onDestroy$ = new Subject<void>();

  constructor(private authService: AuthService, private router: Router, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.entropy$ = this.registerForm.get('password').valueChanges.pipe(map((password) => password ? this.countPasswordEntropy(password) : 0));
  }

  ngOnDestroy(): void {
    this.onDestroy$.next();
    this.onDestroy$.complete();
  }

  registerForm = this.fb.group({
    name: [null as string, [Validators.required, Validators.minLength(4), Validators.maxLength(32), Validators.pattern(/^[a-zA-Z_0-9]{4,32}$/)]],
    email: [null as string, [Validators.required, Validators.minLength(3), Validators.maxLength(64), Validators.pattern(/^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$/)]],
    password: [null as string, [Validators.required, Validators.minLength(8), Validators.maxLength(32), Validators.pattern(/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!^&+=,.()*])(?=\S+$).{8,32}$/)]],
    mfaEnabled: [false]
  },);

  tfaForm = this.fb.control(null as string, [
    Validators.required,
    Validators.minLength(6),
    Validators.maxLength(6),
    Validators.pattern(/^[0-9]{6}$/),
  ]);

  registerUser() {
    // console.log(this.registerForm.value);
    this.message = '';
    this.authService.register(this.registerForm.value as RegisterRequest).subscribe({
      next: (response) => {
        if (response) {
          this.authResponse = response;
        } else {
          this.message =
            'Konto utworzono pomyślnie\nZa 3s nastąpi przekierowanie do logowania';
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
      email: this.registerForm.get('email').value,
      code: this.tfaForm.value,
    };
    this.authService.verifyCode(verifyRequest).subscribe({
      next: (response) => {
        this.message =
          'Konto utworzono pomyślnie\nZa 3s nastąpi przekierowanie do strony głownej';
        setTimeout(() => {
          localStorage.setItem('token', response.accessToken as string);
          this.router.navigate(['home']);
        }, 3000);
      },
    });
  }

  private countPasswordEntropy(password: string): number {
    const charCount: Record<string, number> = {};
    const wordLength = password.length;

    // Oblicz częstość występowania każdego znaku w słowie
    for (const char of password) {
      charCount[char] = (charCount[char] || 0) + 1;
    }

    // Oblicz prawdopodobieństwo wystąpienia każdego znaku
    const probabilities = Object.values(charCount).map(count => count / wordLength);

    // Oblicz entropię na podstawie wzoru: H(X) = -Σ P(x) * log2(P(x))
    const entropy = probabilities.reduce((sum, probability) => {
      return sum - probability * Math.log2(probability);
    }, 0);

    return entropy;
  }
}
