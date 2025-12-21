import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Note, NoteCreateDTO } from '../models/Note';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { createCategory } from '../models/Category';

@Injectable({
  providedIn: 'root',
})
export class NotesService {

  private apiUrl = environment.API_BASE_URL_NOTES;

  constructor(private http: HttpClient) {}

  getActiveNotes() : Observable<Note[]> {
    return this.http.get<Note[]>(`${this.apiUrl}`);
  }

  getArchivedNotes() : Observable<Note[]> {
    return this.http.get<Note[]>(`${this.apiUrl}/archived`);
  }

  getRecentNotes() : Observable<Note[]> {
    return this.http.get<Note[]>(`${this.apiUrl}/most-recent`);
  }

  getNoteById(id:number) {
    return this.http.get<Note>(`${this.apiUrl}/${id}`);
  }

  getByCategory(category:string) : Observable<Note[]> {
    return this.http.get<Note[]>(`${this.apiUrl}/filterByCategory/${category}`)
  }

  addCategoryToNote(name:createCategory, id:number) : Observable<Note>{
    return this.http.put<Note>(`${this.apiUrl}/${id}/addCategory`, name);
  }

  removeCategoryOfNote(idNote:number, idCategory:number) {
    return this.http.put<Note>(`${this.apiUrl}/${idNote}/removeCategory/${idCategory}`, null);
  }

  removeCategoriesOfNote(idNote:number, categoriesIds:number[]) {
    return this.http.delete<Note>(`${this.apiUrl}/${idNote}/categories`, {body:{ categoryIds: categoriesIds}})
  }

  createNote(note : NoteCreateDTO) : Observable<Note> {
    return this.http.post<Note>(`${this.apiUrl}`, note);
  }
  
  saveEditNote(note: NoteCreateDTO, id: number|null) :Observable<Note> {
    return this.http.put<Note>(`${this.apiUrl}/${id}`, note);
  }

  archiveNote(id : number, archive: boolean) : Observable<Note> {
    return this.http.put<Note>(`${this.apiUrl}/${id}/archive/${archive}`, {});
  }

  deleteNote(id : number) {
    return this.http.delete<Note>(`${this.apiUrl}/${id}`);
  }
}
