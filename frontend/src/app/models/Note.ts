import { Category } from "./Category";

export interface Note {
    id : number;
    title : string;
    content : string;
    created_at : Date;
    updated_at : Date;
    isArchived: boolean;
    categories: Category[];
}

export class NoteCreateDTO {
    id?: number | string | null;
    title : string;
    content: string;
    constructor(title : string, content : string) {
        this.title = title;
        this.content = content;
    }
}