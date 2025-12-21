import { Component } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { Note } from '../../../models/Note';
import { NotesService } from '../../../services/notes-service';
import { CommonModule } from '@angular/common';
import { NoteCard } from "../note-card/note-card";
import { Router } from '@angular/router';
import { NoteSyncService } from '../../../services/note-sync-service';

@Component({
  selector: 'app-archive-notes',
  imports: [CommonModule, NoteCard],
  templateUrl: './archive-notes.html',
  styleUrl: './archive-notes.scss',
})
export class ArchiveNotes {

  notes$ !: Observable<Note[]>;

  private syncSubscription!: Subscription;

  constructor(private service : NotesService, private router: Router, private noteSyncService : NoteSyncService) {}

  ngOnInit() {
    this.loadArchivedNotes();
  }

  loadArchivedNotes() {
    this.notes$ = this.noteSyncService.archivedNotes$;
  }

  ngOnDestroy(): void {
    if (this.syncSubscription) {
      this.syncSubscription.unsubscribe();
    }
  }
}
