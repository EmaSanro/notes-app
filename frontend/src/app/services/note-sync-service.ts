import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { NotesService } from './notes-service';
import { Note } from '../models/Note';

@Injectable({
  providedIn: 'root',
})
export class NoteSyncService {
  // 1. BehaviorSubject: Almacena la lista de notas activa.
  // Empieza con un array vacío.
  private _recentNotes$ = new BehaviorSubject<Note[]>([]);
  private _activeNotes$ = new BehaviorSubject<Note[]>([]);
  private _archivedNotes$ = new BehaviorSubject<Note[]>([]);
  
  // 2. Observable público para que los componentes se suscriban (sin emitir).
  public readonly recentNotes$ = this._recentNotes$.asObservable();
  public readonly activeNotes$ = this._activeNotes$.asObservable();
  public readonly archivedNotes$ = this._archivedNotes$.asObservable();

  // Inyectar el servicio HTTP para obtener datos del backend
  constructor(private noteService: NotesService) { 
    // Llamar a cargar datos en el constructor asegura que la lista se llena
    this.loadRecentNotes();
    this.loadActiveNotes();
    this.loadArchivedNotes();
  }

  /**
   * Obtiene la lista completa de notas desde el backend y la emite al BehaviorSubject.
   */
  loadRecentNotes() {
    this.noteService.getRecentNotes().subscribe({
      next: (notes) => {
        // 3. Emitir el nuevo array a todos los suscriptores.
        this._recentNotes$.next(notes); 
      },
      error: (err) => console.error('Error al cargar notas:', err)
    });
  }
  
  loadActiveNotes() {
    this.noteService.getActiveNotes().subscribe({
      next: (notes) => {
        // 3. Emitir el nuevo array a todos los suscriptores.
        this._activeNotes$.next(notes); 
      },
      error: (err) => console.error('Error al cargar notas:', err)
    });
  }
  
  loadArchivedNotes() {
    this.noteService.getArchivedNotes().subscribe({
      next: (notes) => {
        // 3. Emitir el nuevo array a todos los suscriptores.
        this._archivedNotes$.next(notes); 
      },
      error: (err) => console.error('Error al cargar notas:', err)
    });
  }

  /**
   * Método de notificación: Ahora solo llama a cargarNotas(), 
   * lo que actualiza el estado central.
   */
  notifyChangesRecentNotes() {
    // 4. Cualquier cambio (crear, eliminar, archivar) dispara una recarga del estado central.
    this.loadRecentNotes();
  }
  notifyChangesActiveNotes() {
    // 4. Cualquier cambio (crear, eliminar, archivar) dispara una recarga del estado central.
    this.loadActiveNotes();
  }
  notifyChangesArchivedNotes() {
    // 4. Cualquier cambio (crear, eliminar, archivar) dispara una recarga del estado central.
    this.loadArchivedNotes();
  }
}
