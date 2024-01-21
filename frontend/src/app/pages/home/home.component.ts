import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { PublicNotesComponent } from './components/public-notes/public-notes.component';
import { UsersNotesComponent } from './components/users-notes/users-notes.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink, PublicNotesComponent, UsersNotesComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {}
