import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { ActivatedRoute } from '@angular/router';
import {
  Observable,
  BehaviorSubject,
  combineLatest,
  map,
  switchMap,
} from 'rxjs';
import { Note } from '../../core/interfaces/note.interface';
import { NoteService } from '../../core/services/note.service';

@Component({
  selector: 'app-note-details',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
  ],
  templateUrl: './note-details.component.html',
  styleUrl: './note-details.component.scss',
})
export class NoteDetailsComponent {
  note$!: Observable<Note>;

  password = this.fb.control('', [Validators.required]);

  constructor(
    private route: ActivatedRoute,
    private noteService: NoteService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.note$ = this.route.params.pipe(
      map((params) => params['id'] as number),
      switchMap((id) => this.noteService.getDetailedNote(id))
    );
  }
}
