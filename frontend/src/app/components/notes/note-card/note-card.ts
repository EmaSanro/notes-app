import { Component, Input, output, Output } from '@angular/core';
import { Note } from '../../../models/Note';
import { NotesService } from '../../../services/notes-service';
import { Router } from '@angular/router';
import { NoteSyncService } from '../../../services/note-sync-service';
import { FormControl, ReactiveFormsModule, Validators, ɵInternalFormsSharedModule } from "@angular/forms";
import { createCategory } from '../../../models/Category';

@Component({
  selector: 'app-note-card',
  imports: [ɵInternalFormsSharedModule, ReactiveFormsModule],
  templateUrl: './note-card.html',
  styleUrl: './note-card.scss',
})
export class NoteCard {

  @Input()
  note !: Note;

  noteUpdated = output<Note>();


  tagClicked : Boolean = false;

  inputControl : FormControl = new FormControl('', [Validators.required, Validators.maxLength(50), Validators.minLength(5)])

  constructor(private service: NotesService, private router : Router, private noteSyncService : NoteSyncService) { }

  archiveNote(archive: boolean) {
    this.service.archiveNote(this.note.id, archive).subscribe({
      next: (updatedNote : Note) => {
        if(updatedNote.isArchived) {
          this.noteSyncService.notifyChangesArchivedNotes();
          this.noteSyncService.notifyChangesActiveNotes();
          this.noteSyncService.notifyChangesRecentNotes();
        } else {
          this.noteSyncService.notifyChangesArchivedNotes();
          this.noteSyncService.notifyChangesActiveNotes();
          this.noteSyncService.notifyChangesRecentNotes();
        }
      }
    });
  }

  deleteNote() {
    this.service.deleteNote(this.note.id).subscribe({
      next: () => {
        this.noteSyncService.notifyChangesRecentNotes();
        this.noteSyncService.notifyChangesActiveNotes();
        this.noteSyncService.notifyChangesArchivedNotes();
      }
    })
  }

  editNote() {
    this.router.navigate(['/edit-note', this.note.id])
  }

  changeToInput() {
    this.tagClicked = !this.tagClicked;
  }

  addCategoryToNote(category:string, id:number) {
    this.service.addCategoryToNote(new createCategory(category), id).subscribe({
      next: () => {
        this.noteSyncService.notifyChangesRecentNotes();
        this.tagClicked = !this.tagClicked;
        this.inputControl.reset();
      }
    });
  }
}
