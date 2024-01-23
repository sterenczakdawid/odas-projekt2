import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Note, NoteDto, NoteGetDto } from '../interfaces/note.interface';
import { environment } from '../../../environments/environment.development';
import { ENDPOINTS } from '../constants/api.const';

@Injectable({
  providedIn: 'root',
})
export class NoteService {
  private http = inject(HttpClient);

  getPublicNotes(): Observable<Note[]> {
    return this.http.get<Note[]>(`${environment.apiUrl}${ENDPOINTS.PUBLIC_NOTES}`);
  }

  getUsersNotes(): Observable<Note[]> {
    return this.http.get<Note[]>(`${environment.apiUrl}${ENDPOINTS.USER_NOTES}`);
  }

  getDetailedNote(note: NoteGetDto): Observable<Note> {
    return this.http.post<Note>(
      `${environment.apiUrl}${ENDPOINTS.NOTE_DETAILS}`, note
    );
  }

  saveNote(data: NoteDto): Observable<Note> {
    return this.http.post<Note>(`${environment.apiUrl}${ENDPOINTS.ADD_NOTE}`, data);
  }
}
