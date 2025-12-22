import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Category } from '../models/Category';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment-prod';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private apiUrl = environment.API_BASE_URL;

  constructor(private http: HttpClient) { }

  getUsedCategories() : Observable<Category[]> {
    return this.http.get<Category[]>('/category');
  }
}
