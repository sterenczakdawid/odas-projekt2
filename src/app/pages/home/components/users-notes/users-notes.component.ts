import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Note } from '../../../../core/interfaces/note.interface';
import { NoteService } from '../../../../core/services/note.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-users-notes',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './users-notes.component.html',
  styleUrl: './users-notes.component.scss',
})
export class UsersNotesComponent implements OnInit {
  usersNotes$!: Observable<Note[]>;

  linkToDetails = `/details/`;

  constructor(private noteService: NoteService) {}

  ngOnInit(): void {
    this.usersNotes$ = this.noteService.getUsersNotes();
    this.usersNotes$.subscribe(
      (notes: Note[]) => {
        // console.log('Otrzymano notatki:', notes);
        // Tutaj możesz przypisać pobrane notatki do odpowiednich zmiennych w komponencie
      },
      (error) => {
        console.error('Błąd podczas pobierania notatek:', error);
      }
    );
  }
}
