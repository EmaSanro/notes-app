import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Note, NoteCreateDTO } from '../models/Note';
import { Observable } from 'rxjs';
import { createCategory } from '../models/Category';

@Injectable({
  providedIn: 'root',
})
export class NotesService {

  constructor(private http: HttpClient) {}

  getActiveNotes() : Observable<Note[]> {
    return this.http.get<Note[]>('/api/notes');
  }

  getArchivedNotes() : Observable<Note[]> {
    return this.http.get<Note[]>('/api/notes/archived');
  }

  getRecentNotes() : Observable<Note[]> {
    return this.http.get<Note[]>('/api/notes/most-recent');
  }

  getNoteById(id:number) {
    return this.http.get<Note>(`/api/notes/${id}`);
  }

  getByCategory(category:string) : Observable<Note[]> {
    return this.http.get<Note[]>(`/api/notes/filterByCategory/${category}`)
  }

  addCategoryToNote(name:createCategory, id:number) : Observable<Note>{
    return this.http.put<Note>(`/api/notes/${id}/addCategory`, name);
  }

  removeCategoryOfNote(idNote:number, idCategory:number) {
    return this.http.put<Note>(`/api/notes/${idNote}/removeCategory/${idCategory}`, null);
  }

  removeCategoriesOfNote(idNote:number, categoriesIds:number[]) {
    return this.http.delete<Note>(`/api/notes/${idNote}/categories`, {body:{ categoryIds: categoriesIds}})
  }

  createNote(note : NoteCreateDTO) : Observable<Note> {
    return this.http.post<Note>('/api/notes', note);
  }
  
  saveEditNote(note: NoteCreateDTO, id: number|null) :Observable<Note> {
    return this.http.put<Note>(`/api/notes/${id}`, note);
  }

  archiveNote(id : number, archive: boolean) : Observable<Note> {
    return this.http.put<Note>(`/api/notes/${id}/archive/${archive}`, {});
  }

  deleteNote(id : number) {
    return this.http.delete<Note>(`/api/notes/${id}`);
  }
}
