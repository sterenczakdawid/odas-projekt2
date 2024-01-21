import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Note } from '../../../../core/interfaces/note.interface';
import { NoteService } from '../../../../core/services/note.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-public-notes',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './public-notes.component.html',
  styleUrl: './public-notes.component.scss'
})
export class PublicNotesComponent implements OnInit{
  publicNotes$!: Observable<Note[]>;

  constructor(
    private noteService: NoteService
  ) { }

  ngOnInit(): void {
    this.publicNotes$ = this.noteService.getPublicNotes();
  }


}
