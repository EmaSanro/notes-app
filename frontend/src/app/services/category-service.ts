import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Category } from '../models/Category';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private apiUrl = environment.API_BASE_URL_CATEGORIES;

  constructor(private http: HttpClient) { }

  getUsedCategories() : Observable<Category[]> {
    return this.http.get<Category[]>(`${this.apiUrl}/`);
  }
}
