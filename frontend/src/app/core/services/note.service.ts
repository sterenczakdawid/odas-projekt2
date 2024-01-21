import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Note, NoteDto } from '../interfaces/note.interface';
import { environment } from '../../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class NoteService {
  private http = inject(HttpClient);

  getPublicNotes(): Observable<Note[]> {
    return this.http.get<Note[]>(`${environment.apiUrl}api/v1/notes/public`);
  }

  getUsersNotes(): Observable<Note[]> {
    return this.http.get<Note[]>(`${environment.apiUrl}api/v1/notes/my`);
  }

  getDetailedNote(id: number): Observable<Note> {
    return this.http.get<Note>(
      `${environment.apiUrl}api/v1/notes/details/${id}`
    );
  }

  saveNote(data: NoteDto): Observable<Note> {
    return this.http.post<Note>(`${environment.apiUrl}api/v1/notes/add`, data);
  }
}
