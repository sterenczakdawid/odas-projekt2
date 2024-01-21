import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Note } from '../../../../core/interfaces/note.interface';
import { NoteService } from '../../../../core/services/note.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-users-notes',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './users-notes.component.html',
  styleUrl: './users-notes.component.scss',
})
export class UsersNotesComponent implements OnInit {
  usersNotes$!: Observable<Note[]>;

  constructor(private noteService: NoteService) {}

  ngOnInit(): void {
    this.usersNotes$ = this.noteService.getUsersNotes();
  }
}
