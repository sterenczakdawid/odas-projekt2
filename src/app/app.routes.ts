import { Routes } from '@angular/router';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { MainComponent } from './pages/main/main.component';
import { authGuard } from './core/guards/auth.guard';
import { AddNoteComponent } from './pages/add-note/add-note.component';
import { HomeComponent } from './pages/home/home.component';
import { NoteDetailsComponent } from './pages/note-details/note-details.component';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: MainComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
  {
    path: 'add',
    component: AddNoteComponent,
  },
  {
    path: 'details/:id',
    component: NoteDetailsComponent,
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [authGuard],
  },
  {
    path: '**',
    redirectTo: '',
  },
];
