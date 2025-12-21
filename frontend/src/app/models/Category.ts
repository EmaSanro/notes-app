export interface Category {
    id: number;
    name: string;
}

export class createCategory {
    name : string;
    constructor(name:string) {
        this.name = name;
    }
}