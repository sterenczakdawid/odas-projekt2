import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Validators, FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { NoteService } from '../../core/services/note.service';
import { take, catchError, of } from 'rxjs';
import { NoteDto } from '../../core/interfaces/note.interface';
import { MatFormField, MatInputModule } from '@angular/material/input';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-add-note',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatInputModule,
    MatCheckboxModule,
    MatButtonModule,
    MatIconModule,
    MatFormField,
  ],
  templateUrl: './add-note.component.html',
  styleUrl: './add-note.component.scss',
})
export class AddNoteComponent {
  addForm = this.fb.group({
    title: [
      '',
      [Validators.required, Validators.minLength(1), Validators.maxLength(64)],
    ],
    content: [
      '',
      [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(4096),
      ],
    ],
    isEncrypted: [false, Validators.required],
    isPublic: [false, Validators.required],
    password: [''],
  });

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private noteService: NoteService
  ) {}

  handleSubmit(): void {
    // console.log(this.addForm.value);
    if (this.addForm.invalid) return;
    const note: NoteDto = this.addForm.value as NoteDto;
    this.noteService
      .saveNote(note)
      .pipe(
        take(1),
        catchError(() => {
          return of(null);
        })
      )
      .subscribe((note) => {
        if (note) {
          void this.router.navigateByUrl(`/home`);
        } else {
          this.addForm.reset();
        }
      });
  }
}
