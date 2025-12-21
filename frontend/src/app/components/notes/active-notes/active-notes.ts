import { Component, Input } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { Note } from '../../../models/Note';
import { NotesService } from '../../../services/notes-service';
import { CommonModule } from '@angular/common';
import { NoteCard } from "../note-card/note-card";
import { Router } from '@angular/router';
import { NoteSyncService } from '../../../services/note-sync-service';

@Component({
  selector: 'app-active-notes',
  imports: [CommonModule, NoteCard],
  templateUrl: './active-notes.html',
  styleUrl: './active-notes.scss',
})
export class ActiveNotes {
  notes$ !: Observable<Note[]>

  @Input()
  note !: Note;

  private syncSubscription!: Subscription;

  constructor(private service : NotesService, private router : Router, private noteSyncService: NoteSyncService) {}
  
  ngOnInit() {
    this.loadActiveNotes();
  }

  loadActiveNotes() {
    this.notes$ = this.noteSyncService.activeNotes$;
  }

  ngOnDestroy(): void {
    if (this.syncSubscription) {
      this.syncSubscription.unsubscribe();
    }
  }

}
