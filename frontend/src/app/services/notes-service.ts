import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Note, NoteCreateDTO } from '../models/Note';
import { Observable } from 'rxjs';
import { createCategory } from '../models/Category';
import { environment } from '../../environments/environment-prod';

@Injectable({
  providedIn: 'root',
})
export class NotesService {

  private apiUrl = environment.API_BASE_URL;

  constructor(private http: HttpClient) {}

  getActiveNotes() : Observable<Note[]> {
    return this.http.get<Note[]>('/notes');
  }

  getArchivedNotes() : Observable<Note[]> {
    return this.http.get<Note[]>('/notes/archived');
  }

  getRecentNotes() : Observable<Note[]> {
    return this.http.get<Note[]>('/notes/most-recent');
  }

  getNoteById(id:number) {
    return this.http.get<Note>(`/notes/${id}`);
  }

  getByCategory(category:string) : Observable<Note[]> {
    return this.http.get<Note[]>(`/notes/filterByCategory/${category}`)
  }

  addCategoryToNote(name:createCategory, id:number) : Observable<Note>{
    return this.http.put<Note>(`/notes/${id}/addCategory`, name);
  }

  removeCategoryOfNote(idNote:number, idCategory:number) {
    return this.http.put<Note>(`/notes/${idNote}/removeCategory/${idCategory}`, null);
  }

  removeCategoriesOfNote(idNote:number, categoriesIds:number[]) {
    return this.http.delete<Note>(`/notes/${idNote}/categories`, {body:{ categoryIds: categoriesIds}})
  }

  createNote(note : NoteCreateDTO) : Observable<Note> {
    return this.http.post<Note>('/notes', note);
  }
  
  saveEditNote(note: NoteCreateDTO, id: number|null) :Observable<Note> {
    return this.http.put<Note>(`/notes/${id}`, note);
  }

  archiveNote(id : number, archive: boolean) : Observable<Note> {
    return this.http.put<Note>(`/notes/${id}/archive/${archive}`, {});
  }

  deleteNote(id : number) {
    return this.http.delete<Note>(`/notes/${id}`);
  }
}
