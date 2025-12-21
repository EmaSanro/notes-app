import { Component, Input } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { Note } from '../../models/Note';
import { NotesService } from '../../services/notes-service';
import { CommonModule } from '@angular/common';
import { NoteCard } from "../notes/note-card/note-card";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NoteSyncService } from '../../services/note-sync-service';
import { CategoryService } from '../../services/category-service';
import { Category } from '../../models/Category';

@Component({
  selector: 'app-home',
  imports: [CommonModule, NoteCard, ReactiveFormsModule],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {

  notes$ !: Observable<Note[]>;
  categories$ !: Observable<Category[]>;

  showForm: Boolean = false;
  showCategories : Boolean = false;
  currentFilter : string = "";

  private syncSubscription!: Subscription;

  @Input()
  note !: Note;

  constructor(private service : NotesService, private categoryService : CategoryService, private router: Router, private noteSyncService : NoteSyncService) {}
  
  formCreateNote : FormGroup = new FormGroup({
    title: new FormControl('', [Validators.required, Validators.maxLength(100)]),
    content: new FormControl('', [Validators.required, Validators.minLength(5)])
  })
  
  getTitle() {
    return this.formCreateNote.get('title');
  }

  getContent() {
    return this.formCreateNote.get('content');
  }


  ngOnInit() {
    this.loadRecentNotes();
    this.loadCategories();
  }

  loadRecentNotes() {
    this.notes$ = this.noteSyncService.recentNotes$;
  }

  loadCategories() {
    this.categories$ = this.categoryService.getUsedCategories();
  }

  showFormCreate() {
    this.showForm = !this.showForm;
    if(this.showCategories) {
      this.showCategories = false;
    }
  }

  createNote() {
    const newNote : Note = this.formCreateNote.value;

    this.service.createNote(newNote).subscribe({
      next: () => {
        this.showForm = !this.showForm;
        this.noteSyncService.notifyChangesRecentNotes();
        this.noteSyncService.notifyChangesActiveNotes();
        this.formCreateNote.reset();
      }
    });
  }

  showCategoriesFunc() {
    this.showCategories = !this.showCategories; 
    if(this.showForm) {
      this.showForm = false;
    }
  }

  getNotesByCategory(category : string) {
    this.currentFilter = category;
    this.showCategories = !this.showCategories;
    this.notes$ = this.service.getByCategory(category);
  }

  deleteFilter() {
    this.loadRecentNotes();
    this.currentFilter = "";
  }

  ngOnDestroy(): void {
    if (this.syncSubscription) {
      this.syncSubscription.unsubscribe();
    }
  }
}
