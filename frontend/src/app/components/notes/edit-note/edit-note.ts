import { Component, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators, ɵInternalFormsSharedModule } from '@angular/forms';
import { NotesService } from '../../../services/notes-service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Observable, of, switchMap } from 'rxjs';
import { Note, NoteCreateDTO } from '../../../models/Note';
import { NoteSyncService } from '../../../services/note-sync-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-edit-note',
  imports: [ɵInternalFormsSharedModule, ReactiveFormsModule, RouterLink, CommonModule],
  templateUrl: './edit-note.html',
  styleUrl: './edit-note.scss',
})
export class EditNote {
  
  noteId !: number | null
  note !: Observable<Note>;
  categoriesToDelete : number[] = [];
  selectedTags = signal<Set<number>>(new Set())

  constructor(private noteService : NotesService, private route : ActivatedRoute, private router: Router, private noteSyncService : NoteSyncService) {  }

  editForm = new FormGroup({
    title: new FormControl<string>('', [Validators.required, Validators.maxLength(100)]),
    content: new FormControl<string>('', [Validators.required, Validators.minLength(5)])
  })

  ngOnInit() {
    this.route.paramMap.pipe(
      switchMap(params => {
        const idString = params.get('id');
        this.noteId = idString ? +idString : null;
        if(this.noteId) {
          this.note = this.noteService.getNoteById(this.noteId);
          return this.noteService.getNoteById(this.noteId);
        }
        return of(null);
      })
    ).subscribe({
      next: (note) => {
        if(note) {
          this.editForm.setValue({'title':note.title, 'content': note.content});
        }
      }
    })
  }

  saveNote() {
    if(this.categoriesToDelete.length != 0 && this.noteId != null) {
      this.noteService.removeCategoriesOfNote(this.noteId, this.categoriesToDelete).subscribe({
        next: () => {
          this.noteSyncService.notifyChangesRecentNotes();
          this.noteSyncService.notifyChangesActiveNotes();
          this.noteSyncService.notifyChangesArchivedNotes();
        }
      });
    }
    const editedNote : NoteCreateDTO = this.editForm.value as Note;
    this.noteService.saveEditNote(editedNote, this.noteId).subscribe({
      next: () => {
        this.router.navigate(["/"]);
        this.noteSyncService.notifyChangesRecentNotes();
        this.noteSyncService.notifyChangesActiveNotes();
        this.noteSyncService.notifyChangesArchivedNotes();
      }
    })
  }

  markToDelete(categoryId:number) {
    this.categoriesToDelete.push(categoryId);
    this.selectedTags.update(set => {
      const newSet = new Set(set);
      newSet.has(categoryId) ? newSet.delete(categoryId) : newSet.add(categoryId);
      return newSet;
    });
    console.log(this.categoriesToDelete)
  }

  isSelected(tagId: number): boolean {
    return this.selectedTags().has(tagId);
  }
}
