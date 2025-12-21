import { Routes } from '@angular/router';
import { ActiveNotes } from './components/notes/active-notes/active-notes';
import { ArchiveNotes } from './components/notes/archive-notes/archive-notes';
import { Home } from './components/home/home';
import { EditNote } from './components/notes/edit-note/edit-note';

export const routes: Routes = [
    {path: "", component: Home},
    {path: "active-notes", component: ActiveNotes},
    {path: "archived-notes", component: ArchiveNotes},
    {path: "edit-note/:id", component: EditNote},
    {path: '**', redirectTo: "", pathMatch: 'full'}
];
