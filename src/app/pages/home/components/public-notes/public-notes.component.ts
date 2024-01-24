import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Note } from '../../../../core/interfaces/note.interface';
import { NoteService } from '../../../../core/services/note.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { environment } from '../../../../../environments/environment.development';

@Component({
  selector: 'app-public-notes',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './public-notes.component.html',
  styleUrl: './public-notes.component.scss'
})
export class PublicNotesComponent implements OnInit{
  publicNotes$!: Observable<Note[]>;

  linkToDetails = `/details/`;

  constructor(
    private noteService: NoteService
  ) { }

  ngOnInit(): void {
    this.publicNotes$ = this.noteService.getPublicNotes();
  }


}
