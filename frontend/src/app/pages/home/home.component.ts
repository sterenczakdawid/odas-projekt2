import { Component, NgZone, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { PublicNotesComponent } from './components/public-notes/public-notes.component';
import { UsersNotesComponent } from './components/users-notes/users-notes.component';
import { AuthService } from '../../core/services/auth.service';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink, PublicNotesComponent, UsersNotesComponent, MatButtonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  private authService = inject(AuthService);
  private _ngZone = inject(NgZone);
  private router = inject(Router);

  logout() {
    this.authService.removeToken();
    this._ngZone.run(() => {
      this.router.navigateByUrl('').then(() => window.location.reload());
    });
  }
}
